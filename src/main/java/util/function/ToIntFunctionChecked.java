package util.function;

import java.sql.SQLException;

@FunctionalInterface
public interface ToIntFunctionChecked<T>{
    int applyAsInt(T value) throws SQLException;
}
