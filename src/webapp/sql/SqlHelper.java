package webapp.sql;

import webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T executeQuery(String query, FunctionWithException<PreparedStatement, T> func) {
        T result;
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            result = func.apply(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
        return result;
    }
}
