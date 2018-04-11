package util.function;

@FunctionalInterface
public interface CheckedSupply<T> {
    T get() throws Exception;
}
