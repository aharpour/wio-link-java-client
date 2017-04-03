package nl.openweb.iot.dashboard.service.script;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.openweb.iot.wio.scheduling.ScheduledTask;

public abstract class AbstractGroovyTaskHandler implements ScheduledTask {

    protected final static Logger LOGGER = LoggerFactory.getLogger(AbstractGroovyTaskHandler.class);

    protected double period;

    public void setPeriod(double period) {
        this.period = period;
    }
}
