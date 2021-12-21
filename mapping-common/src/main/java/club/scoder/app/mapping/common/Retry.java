package club.scoder.app.mapping.common;

public class Retry {

    public static final boolean MAX_LIMIT_ENABLE = true;

    public static final boolean MAX_LIMIT_DISABLE = false;

    /**
     * max retries.
     */
    private static final int DEFAULT_MAX_RETRIES = 10;

    /**
     * maximum waiting interval each time(s).
     */
    private static final int DEFAULT_MAX_PER_WAIT_INTERVAL = 120;

    private final boolean enableMaxRetries;

    private final int maxRetries;

    private final int maxPerWaitInterval;

    private final int factor;

    private int gear;

    /**
     * number of retries.
     */
    private int retries;


    public Retry() {
        this(MAX_LIMIT_DISABLE, 0, DEFAULT_MAX_PER_WAIT_INTERVAL, 1, 0);
    }

    public Retry(boolean enableMaxRetries) {
        this(enableMaxRetries, DEFAULT_MAX_RETRIES, DEFAULT_MAX_PER_WAIT_INTERVAL, 1, 0);
    }

    public Retry(boolean enableMaxRetries, int maxRetries, int maxPerWaitInterval) {
        this(enableMaxRetries, maxRetries, maxPerWaitInterval, 1, 0);
    }

    /**
     * @param enableMaxRetries   Whether to enable the maximum retries limit
     * @param maxRetries         max retries
     * @param maxPerWaitInterval max per wait interval (seconds)
     * @param factor             base used for to each offset
     * @param gear               initial position
     */
    public Retry(boolean enableMaxRetries, int maxRetries, int maxPerWaitInterval, int factor, int gear) {
        this.enableMaxRetries = enableMaxRetries;
        this.maxRetries = maxRetries;
        this.maxPerWaitInterval = maxPerWaitInterval;
        this.factor = factor;
        this.gear = gear;
    }

    public int getRetries() {
        return retries;
    }

    public long waitMilliseconds() {
        int seconds = waitSeconds();
        if (seconds < 0) return seconds;
        return seconds * 1000L;
    }

    private int waitSeconds() {
        if (enableMaxRetries && retries >= maxRetries) {
            return -1;
        }
        int seconds = factor << gear++;
        if (seconds > maxPerWaitInterval) {
            resetGear();
            seconds = factor << gear++;
        }
        retries++;
        return seconds;
    }

    private void resetGear() {
        this.gear = 0;
    }

}
