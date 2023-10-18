package webapp.storage;

import webapp.exception.NotExistStorageException;
import webapp.model.ContactType;
import webapp.model.Resume;
import webapp.sql.ConnectionFactory;
import webapp.sql.SqlHelper;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {

    private final SqlHelper helper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        ConnectionFactory connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        helper = new SqlHelper(connectionFactory);
    }

    @Override
    public void clear() {
        helper.executeQuery("TRUNCATE resume CASCADE", PreparedStatement::execute);
    }

    @Override
    public Resume get(String uuid) {
        return helper.executeQuery("" +
                        "    SELECT * FROM resume r " +
                        " LEFT JOIN contact c " +
                        "        ON r.uuid = c.resume_uuid " +
                        "     WHERE r.uuid =? ",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    do {
                        setResumeContact(resume, rs);
                    } while (rs.next());

                    return resume;
                });
    }

    @Override
    public void save(Resume resume) {
        helper.transactionalExecute(resume.getUuid(),
                conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                        ps.setString(1, resume.getUuid());
                        ps.setString(2, resume.getFullName());
                        ps.execute();
                    }
                    insertContacts(conn, resume);
                    return null;
                }
        );
    }

    @Override
    public void update(Resume resume) {
        helper.transactionalExecute(resume.getUuid(),
                conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                        ps.setString(1, resume.getFullName());
                        ps.setString(2, resume.getUuid());
                        executeUpdate(ps, resume.getUuid());
                    }
                    try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid = ?")) {
                        ps.setString(1, resume.getUuid());
                        ps.execute();
                    }
                    insertContacts(conn, resume);
                    return null;
                }
        );
    }

    @Override
    public void delete(String uuid) {
        helper.executeQuery("DELETE FROM resume WHERE uuid = ?",
                (ps) -> {
                    ps.setString(1, uuid);
                    executeUpdate(ps, uuid);
                    return null;
                });
    }

    @Override
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
    }

    @Override
    public int size() {
        return helper.executeQuery("SELECT count(*) FROM resume",
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

    private void setResumeContact(Resume resume, ResultSet rs) throws SQLException {
        String type = rs.getString("type");
        if (type != null) {
            String value = rs.getString("value");
            resume.setContact(ContactType.valueOf(type), value);
        }
    }

    private void insertContacts(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
}
