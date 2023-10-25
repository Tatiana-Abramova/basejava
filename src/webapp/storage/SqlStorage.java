package webapp.storage;

import webapp.exception.NotExistStorageException;
import webapp.exception.StorageException;
import webapp.model.*;
import webapp.sql.ConnectionFactory;
import webapp.sql.ResumeFiller;
import webapp.sql.SqlHelper;

import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static webapp.storage.AbstractStorage.RESUME_COMPARATOR;
import static webapp.utils.Utils.getLineSeparator;

public class SqlStorage implements Storage {

    private final SqlHelper helper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new StorageException(e);
        }
        ConnectionFactory connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        helper = new SqlHelper(connectionFactory);
    }

    @Override
    public void clear() {
        helper.executeQuery("TRUNCATE resume CASCADE", PreparedStatement::execute);
    }

    @Override
    public Resume get(String uuid) {
        return helper.executeQuery("""
                        SELECT
                                r.uuid
                                , r.full_name
                                , c.type as "contact_type"
                                , c.value as "contact_value"
                                , s.type as "section_type"
                                , s.value as "section_value"
                            FROM resume r
                        LEFT JOIN contact c
                            ON r.uuid = c.resume_uuid
                        LEFT JOIN section s
                            ON r.uuid = s.resume_uuid
                            WHERE r.uuid =?\s""".indent(2),
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    do {
                        setContact(resume, rs);
                        setSection(resume, rs);
                    } while (rs.next());

                    return resume;
                });
    }

    @Override
    public void save(Resume resume) {
        helper.transactionalExecute(resume.getUuid(),
                conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("" +
                            "INSERT INTO resume (uuid, full_name)" +
                            " VALUES (?,?)")) {
                        ps.setString(1, resume.getUuid());
                        ps.setString(2, resume.getFullName());
                        ps.execute();
                    }
                    insertContacts(conn, resume);
                    insertSections(conn, resume);
                    return null;
                }
        );
    }

    @Override
    public void update(Resume resume) {
        helper.transactionalExecute(resume.getUuid(),
                conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("" +
                            "UPDATE resume" +
                            " SET full_name = ? " +
                            "WHERE uuid = ?")) {
                        ps.setString(1, resume.getFullName());
                        ps.setString(2, resume.getUuid());
                        executeUpdate(ps, resume.getUuid());
                    }
                    delete(conn, resume.getUuid(), "contact");
                    delete(conn, resume.getUuid(), "section");
                    insertContacts(conn, resume);
                    insertSections(conn, resume);
                    return null;
                }
        );
    }

    @Override
    public void delete(String uuid) {
        helper.executeQuery("" +
                        "DELETE FROM resume" +
                        " WHERE uuid = ?",
                (ps) -> {
                    ps.setString(1, uuid);
                    executeUpdate(ps, uuid);
                    return null;
                });
    }

/*    @Override
    public List<Resume> getAllSorted() {
        return helper.executeQuery("" +
                        "   SELECT * FROM resume r " +
                        "LEFT JOIN contact c " +
                        "       ON r.uuid = c.resume_uuid" +
                        "   order by r.full_name, r.uuid",
                (ps) -> {
                    Map<String, Resume> resumes = new HashMap<>();
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        String uuid = rs.getString("uuid");
                        Resume resume = resumes.get(uuid);
                        if (resume == null) {
                            resume = new Resume(uuid, rs.getString("full_name"));
                            resumes.put(uuid, resume);
                        }
                        setResumeContact(resume, rs);
                    }
                    return resumes.values().stream().toList();
                });
    }*/

    @Override
    public List<Resume> getAllSorted() {
        return helper.transactionalExecute(null,
                conn -> {
                    Map<String, Resume> resumes = new HashMap<>();
                    try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r")) {
                        ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            String uuid = rs.getString("uuid");
                            resumes.putIfAbsent(
                                    uuid,
                                    new Resume(uuid, rs.getString("full_name")));
                        }
                    }
                    fillResumes(conn, resumes, "contact", this::setContact);
                    fillResumes(conn, resumes, "section", this::setSection);
                    return resumes.values().stream().sorted(RESUME_COMPARATOR).toList();
                }
        );
    }

    @Override
    public int size() {
        return helper.executeQuery("" +
                        "SELECT count(*)" +
                        " FROM resume",
                (ps) -> {
                    ResultSet rs = ps.executeQuery();
                    rs.next();
                    return rs.getInt(1);
                });
    }

    private void executeUpdate(PreparedStatement ps, String uuid) throws SQLException {
        int count = ps.executeUpdate();
        if (count == 0) {
            throw new NotExistStorageException(uuid);
        }
    }

    private void fillResumes(Connection conn, Map<String, Resume> resumes, String entity, ResumeFiller<Resume, ResultSet> filler) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("" +
                "SELECT" +
                "       type as \"" + entity + "_type\"" +
                "       , value as \"" + entity + "_value\"" +
                "       , resume_uuid" +
                "   FROM " + entity +
                "   WHERE resume_uuid = ANY (?)")) {
            Array resumeUuids = ps
                    .getConnection()
                    .createArrayOf("VARCHAR", resumes.keySet().toArray());
            ps.setArray(1, resumeUuids);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Resume resume = resumes.get(rs.getString("resume_uuid"));
                filler.fill(resume, rs);
            }
        }
    }

    private void setContact(Resume resume, ResultSet rs) throws SQLException {
        String type = rs.getString("contact_type");
        if (type != null) {
            String value = rs.getString("contact_value");
            resume.setContact(ContactType.valueOf(type), value);
        }
    }

    private void setSection(Resume resume, ResultSet rs) throws SQLException {
        String type = rs.getString("section_type");
        if (type != null) {
            String value = rs.getString("section_value");
            Section section = null;
            switch (SectionType.valueOf(type)) {
                case PERSONAL, OBJECTIVE -> section = new TextSection(value);
                case ACHIEVEMENT, QUALIFICATIONS -> {
                    section = new ListSection();
                    ((ListSection) section)
                            .getList()
                            .addAll(Arrays.stream(value.split(getLineSeparator())).toList());
                }
                case EXPERIENCE, EDUCATION -> {
                }
            }
            resume.setSection(SectionType.valueOf(type), section);
        }
    }

    private void insertContacts(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(getInsertStatement("contact"))) {
            for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSections(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(getInsertStatement("section"))) {
            for (Map.Entry<SectionType, Section> e : resume.getSections().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                Section section = e.getValue();
                switch (SectionType.valueOf(e.getKey().name())) {
                    case PERSONAL, OBJECTIVE -> ps.setString(3, ((TextSection) section).getText());
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        String values = String.join(
                                getLineSeparator(),
                                ((ListSection) section).getList());
                        ps.setString(3, values);
                    }
                    case EXPERIENCE, EDUCATION -> {
                    }
                }
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private String getInsertStatement(String entity) {
        return "INSERT INTO " + entity + " (resume_uuid, type, value)" +
                " VALUES (?,?,?)";
    }

    private void delete(Connection conn, String uuid, String entity) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("" +
                "DELETE FROM " + entity +
                " WHERE resume_uuid = ?")) {
            ps.setString(1, uuid);
            ps.execute();
        }
    }
}
