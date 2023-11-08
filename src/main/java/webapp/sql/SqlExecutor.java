package webapp.sql;

import java.sql.SQLException;

@FunctionalInterface
public interface SqlExecutor<T, R> {
    R apply(T t) throws SQLException;
}
