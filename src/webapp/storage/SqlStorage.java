package webapp.storage;

import webapp.exception.ExistStorageException;
import webapp.exception.NotExistStorageException;
import webapp.model.Resume;
import webapp.sql.ConnectionFactory;
import webapp.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {

    private static final String RESUME = "resume";
    private static final String UUID = "uuid";
    private static final String FULL_NAME = "full_name";
    private final SqlHelper helper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        ConnectionFactory connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        helper = new SqlHelper(connectionFactory);
    }

    @Override
    public void clear() {
        helper.executeQuery(String.format("TRUNCATE %s CASCADE", RESUME), PreparedStatement::execute);
    }

    @Override
    public Resume get(String uuid) {
        return helper.executeQuery(
                String.format("SELECT * FROM %s r WHERE r.%s =?", RESUME, UUID),
                (ps) -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    return new Resume(uuid, rs.getString(FULL_NAME));
                });
    }

    @Override
    public void update(Resume r) {
        get(r.getUuid());
        helper.executeQuery(
                String.format("UPDATE %s SET %s = ? WHERE %s = ?",
                        RESUME,
                        FULL_NAME,
                        UUID),
                (ps) -> {
                    ps.setString(1, r.getFullName());
                    ps.setString(2, r.getUuid());
                    ps.execute();
                    return null;
                });
    }

    @Override
    public void save(Resume r) {
        try {
            get(r.getUuid());
        } catch (NotExistStorageException e) {
            helper.executeQuery(
                    String.format("INSERT INTO %s (%s, %s) VALUES (?,?)",
                            RESUME,
                            UUID,
                            FULL_NAME),
                    (ps) -> {
                        ps.setString(1, r.getUuid());
                        ps.setString(2, r.getFullName());
                        ps.execute();
                        return null;
                    });
            return;
        }
        throw new ExistStorageException(r.getUuid());
    }

    @Override
    public void delete(String uuid) {
        helper.executeQuery(
                String.format("DELETE FROM %s WHERE %s = ?",
                        RESUME,
                        UUID),
                (ps) -> {
                    ps.setString(1, uuid);
                    ps.execute();
                    return null;
                });
    }

    @Override
    public List<Resume> getAllSorted() {
        return helper.executeQuery(
                String.format("SELECT * FROM %s r order by r.%s, r.%s",
                        RESUME,
                        FULL_NAME,
                        UUID),
                (ps) -> {
                    List<Resume> resumes = new ArrayList<>();
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        resumes.add(new Resume(rs.getString(1).trim(), rs.getString(2)));
                    }// тут добавляются пробелы в поле uuid, поэтому добавила trim()
                    // но в get() почему-то результат возвращается с обрезанными пробелами
                    // зависит от наличия параметров в запросе
                    return resumes;
                });
    }

    @Override
    public int size() {
        return helper.executeQuery(String.format("SELECT count(*) FROM %s", RESUME), (ps) -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        });
    }
}
