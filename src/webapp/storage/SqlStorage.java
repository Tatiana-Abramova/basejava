package webapp.storage;

import webapp.exception.NotExistStorageException;
import webapp.model.Resume;
import webapp.sql.ConnectionFactory;
import webapp.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        return helper.executeQuery(uuid, "SELECT * FROM resume r WHERE r.uuid =?",
                (ps) -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    return new Resume(uuid, rs.getString("full_name"));
                });
    }

    @Override
    public void save(Resume r) {
        helper.executeQuery(
                r.getUuid(),
                "INSERT INTO resume (uuid, full_name) VALUES (?,?)",
                (ps) -> {
                    ps.setString(1, r.getUuid());
                    ps.setString(2, r.getFullName());
                    ps.execute();
                    return null;
                });
    }

    @Override
    public void update(Resume r) {
        get(r.getUuid());
        helper.executeQuery("UPDATE resume SET full_name = ? WHERE uuid = ?",
                (ps) -> {
                    ps.setString(1, r.getFullName());
                    ps.setString(2, r.getUuid());
                    executeUpdate(ps, r.getUuid());
                    return null;
                });
    }

    @Override
    public void delete(String uuid) {
        get(uuid);
        helper.executeQuery("DELETE FROM resume WHERE uuid = ?",
                (ps) -> {
                    ps.setString(1, uuid);
                    executeUpdate(ps, uuid);
                    return null;
                });
    }

    private void executeUpdate(PreparedStatement ps, String uuid) throws SQLException {
        int count = ps.executeUpdate();
        if (count == 0) {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        return helper.executeQuery("SELECT * FROM resume r order by r.full_name, r.uuid",
                (ps) -> {
                    List<Resume> resumes = new ArrayList<>();
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        resumes.add(new Resume(rs.getString(1), rs.getString(2)));
                    }
                    return resumes;
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
}
