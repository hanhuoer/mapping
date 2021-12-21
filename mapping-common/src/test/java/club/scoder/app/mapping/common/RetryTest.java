package club.scoder.app.mapping.common;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RetryTest {

    @Test
    public void test1() {
        Retry retry = new Retry();
        for (int i = 0; i < 20; i++) {
            long ms = retry.waitMilliseconds();
            System.out.println(ms);
            if (i == 0) Assertions.assertEquals(ms, 1000L);
            if (i == 1) Assertions.assertEquals(ms, 2000L);
            if (i == 2) Assertions.assertEquals(ms, 4000L);
            if (i == 4) Assertions.assertEquals(ms, 16000L);
            if (i == 6) Assertions.assertEquals(ms, 64000L);
        }
    }

    @Test
    public void test2() {
        Retry retry = new Retry(Retry.MAX_LIMIT_ENABLE);
        for (int i = 0; i < 20; i++) {
            long ms = retry.waitMilliseconds();
            System.out.println(ms);
            if (i > 9) {
                Assertions.assertEquals(ms, -1);
            }
        }
    }

}