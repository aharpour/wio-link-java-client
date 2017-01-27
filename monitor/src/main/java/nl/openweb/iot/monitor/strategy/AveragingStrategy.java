package nl.openweb.iot.monitor.strategy;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.springframework.beans.factory.annotation.Autowired;

import nl.openweb.iot.monitor.MonitorSettings;
import nl.openweb.iot.monitor.ReadingStrategy;
import nl.openweb.iot.wio.WioException;

public class AveragingStrategy implements ReadingStrategy {

    private MonitorSettings settings;


    @Override
    public double readDouble(Supplier<Double> supplier) throws WioException {
        DescriptiveStatistics statistics = new DescriptiveStatistics();
        for (int i = 0; i < settings.getNumberOfReadingToAverage(); i++) {
            statistics.addValue(supplier.get());
        }
        return statistics.getMean();
    }

    @Override
    public int readInteger(Supplier<Integer> supplier) throws WioException {
        DescriptiveStatistics statistics = new DescriptiveStatistics();
        for (int i = 0; i < settings.getNumberOfReadingToAverage(); i++) {
            statistics.addValue(supplier.get());
        }
        return new Long(Math.round(statistics.getMean())).intValue();
    }

    @Autowired
    public void setSettings(MonitorSettings settings) {
        this.settings = settings;
    }
}
