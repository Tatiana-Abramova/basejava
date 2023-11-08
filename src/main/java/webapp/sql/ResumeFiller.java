package webapp.sql;

import java.sql.SQLException;

@FunctionalInterface
public interface ResumeFiller<T, R> {
    void fill(T t, R r) throws SQLException;
}
