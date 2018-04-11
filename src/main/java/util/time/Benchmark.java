package util.time;

public class Benchmark {
    public static long of(Runnable r) {
        long start = System.currentTimeMillis();
        r.run();
        return System.currentTimeMillis() - start;
    }
}
