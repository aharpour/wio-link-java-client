package nl.openweb.iot.dashboard.service.util;


public class DecayUtils {

    private final long periodInSec;

    public DecayUtils(long periodInSec) {
        this.periodInSec = periodInSec;
    }

    public double decay(double value, long timePastInMillis) {
        double time = ((double) timePastInMillis) / (1000 * periodInSec);
        return Math.max(value * Math.exp(-1 * Math.abs(time)), 0);
    }
}
