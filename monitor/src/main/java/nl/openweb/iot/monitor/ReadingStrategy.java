package nl.openweb.iot.monitor;

import nl.openweb.iot.wio.WioException;

public interface ReadingStrategy {

    double readDouble(Supplier<Double> supplier) throws WioException;

    int readInteger(Supplier<Integer> supplier) throws WioException;

    @FunctionalInterface
    public interface Supplier<T> {
        public T get() throws WioException;
    }
}

