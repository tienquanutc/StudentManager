package util.function;

@FunctionalInterface
public interface CheckedRunable {
    void run() throws Exception;

}
