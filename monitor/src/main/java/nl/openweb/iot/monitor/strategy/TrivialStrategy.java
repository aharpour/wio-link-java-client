package nl.openweb.iot.monitor.strategy;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

import nl.openweb.iot.monitor.ReadingStrategy;
import nl.openweb.iot.wio.WioException;

@ConditionalOnMissingBean(ReadingStrategy.class)
public class TrivialStrategy implements ReadingStrategy {

    @Override
    public double readDouble(Supplier<Double> supplier) throws WioException {
        return supplier.get();
    }

    @Override
    public int readInteger(Supplier<Integer> supplier) throws WioException {
        return supplier.get();
    }
}
