package nl.openweb.iot.dashboard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.openweb.iot.dashboard.domain.Task;
import nl.openweb.iot.monitor.Monitor;
import nl.openweb.iot.monitor.ReadingStrategy;
import nl.openweb.iot.monitor.repository.ReadingRepository;
import nl.openweb.iot.wio.scheduling.ScheduledTask;

@Service
public class MonitorTaskHandlerFactory implements TaskHandlerFactory {

    private final ReadingRepository repository;
    private final ReadingStrategy strategy;

    @Autowired
    public MonitorTaskHandlerFactory(ReadingRepository repository, ReadingStrategy strategy) {
        this.repository = repository;
        this.strategy = strategy;
    }

    @Override
    public ScheduledTask build(Task task) {
        return new Monitor((int) Math.round(task.getPeriod() * 60), repository, strategy);
    }
}
