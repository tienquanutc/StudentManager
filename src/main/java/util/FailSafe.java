package util;

import app.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.function.CheckedRunable;
import util.function.CheckedSupply;

import java.util.function.Consumer;

public class FailSafe {
    protected static Logger LOG = LoggerFactory.getLogger(FailSafe.class);

    private Consumer<Exception> onRetry;

    public FailSafe onRetry(Consumer<Exception> onRetry) {
        this.onRetry = onRetry;
        return this;
    }


    public void run(CheckedRunable r) {
        boolean success = false;
        while (!success) {
            try {
                r.run();
            } catch (Exception e) {
                LOG.debug("on retry " + e.getMessage());
                if (onRetry != null) {
                    onRetry.accept(e);
                }
                continue;
            }
            success = true;
        }
    }

    public <T> T get(CheckedSupply<T> supply) {
        while (true) {
            try {
                return supply.get();
            } catch (Exception e) {
                LOG.debug("on retry " + e.getMessage());
                if (onRetry != null) {
                    onRetry.accept(e);
                }
            }
        }
    }

    public static FailSafe withOnRetry(Consumer<Exception> onRetry) {
        FailSafe failSafe = new FailSafe();
        failSafe.onRetry = onRetry;
        return failSafe;
    }
}
