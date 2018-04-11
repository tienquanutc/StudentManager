package util.function;

import java.sql.SQLException;

@FunctionalInterface
public interface ToIntConsumerChecked<T> {
    int accept(T value) throws SQLException;
}
