package util.function;


@FunctionalInterface
public interface FunctionChecked<T, R>{
    R apply(T t) throws Exception;
}
