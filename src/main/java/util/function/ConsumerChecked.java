package util.function;

import java.sql.SQLException;

@FunctionalInterface
public interface ConsumerChecked<T> {
    void accept(T value) throws SQLException;
}
