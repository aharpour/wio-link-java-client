package nl.openweb.iot.monitor;


import java.util.function.BiConsumer;

import lombok.Setter;
import nl.openweb.iot.monitor.domain.Reading;
import nl.openweb.iot.monitor.repository.ReadingRepository;
import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.domain.Grove;
import nl.openweb.iot.wio.domain.Node;
import nl.openweb.iot.wio.scheduling.ScheduledTask;
import nl.openweb.iot.wio.scheduling.SchedulingUtils;
import nl.openweb.iot.wio.scheduling.TaskContext;

public class Monitor implements ScheduledTask {

    private final int period;
    private final ReadingRepository readingRepository;

    @Setter
    private BiConsumer<Reading, TaskContext> alertMonitor;

    public Monitor(int period, ReadingRepository readingRepository) {
        this.period = period;
        this.readingRepository = readingRepository;
    }

    @Override
    public TaskExecutionResult execute(Node node, TaskContext context) throws WioException {
        ReadingRepository repository = context.getBean(ReadingRepository.class);
        Reading reading = new Reading(node.getName());
        for (Grove grove : node.getGroves()) {
            reading.addData(grove);
        }
        repository.save(reading);
        if (alertMonitor != null) {
            alertMonitor.accept(reading, context);
        }
        return SchedulingUtils.minutesLater(period);
    }
}
