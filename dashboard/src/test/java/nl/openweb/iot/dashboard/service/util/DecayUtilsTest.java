package nl.openweb.iot.dashboard.service.util;

import org.junit.Assert;
import org.junit.Test;


public class DecayUtilsTest {

    private static final int PERIOD_IN_SEC = 60 * 60;
    private static final int PERIOD_IN_MILLIS = PERIOD_IN_SEC * 1000;
    private DecayUtils decayUtils = new DecayUtils(PERIOD_IN_SEC);

    @Test
    public void oneAtZero() throws Exception {
        Assert.assertEquals(100.0, decayUtils.decay(100, 0), 0.01);
    }

    @Test
    public void multiplicative() {
        double actual = 100.0;
        for (int i = 1; i < 36; i++) {
            double expected = decayUtils.decay(100, (i * PERIOD_IN_MILLIS) / 3);
            actual = decayUtils.decay(actual, PERIOD_IN_MILLIS / 3);
            Assert.assertEquals(expected, actual, 0.0001);
        }
    }

    @Test
    public void symmetric() {
        for (int i = 0; i < 36; i++) {
            Assert.assertEquals(decayUtils.decay(100, (i * PERIOD_IN_MILLIS) / 3),
                decayUtils.decay(100, -(i * PERIOD_IN_MILLIS) / 3), 0.0001);
        }
    }

    @Test
    public void decreasing() {
        for (int i = 0; i < 36; i++) {
            Assert.assertTrue(decayUtils.decay(100, (i * PERIOD_IN_MILLIS) / 3) >=
                decayUtils.decay(100, ((i + 1) * PERIOD_IN_MILLIS)) / 3);
        }
    }

}
