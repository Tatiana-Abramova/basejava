package webapp.sql;

import java.sql.SQLException;

@FunctionalInterface
public interface FunctionWithException<T, R> {
    R apply(T t) throws SQLException;
}
