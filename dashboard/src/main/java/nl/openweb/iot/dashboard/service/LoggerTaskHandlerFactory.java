package nl.openweb.iot.dashboard.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.openweb.iot.dashboard.domain.Task;
import nl.openweb.iot.monitor.Monitor;
import nl.openweb.iot.monitor.ReadingStrategy;
import nl.openweb.iot.monitor.domain.Reading;
import nl.openweb.iot.monitor.repository.ReadingRepository;
import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.domain.Grove;
import nl.openweb.iot.wio.domain.Node;
import nl.openweb.iot.wio.scheduling.ScheduledTask;
import nl.openweb.iot.wio.scheduling.SchedulingUtils;
import nl.openweb.iot.wio.scheduling.TaskContext;

@Service
public class LoggerTaskHandlerFactory implements TaskHandlerFactory {

    private final ReadingRepository repository;
    private final ReadingStrategy strategy;

    @Autowired
    public LoggerTaskHandlerFactory(ReadingRepository repository, ReadingStrategy strategy) {
        this.repository = repository;
        this.strategy = strategy;
    }

    @Override
    public ScheduledTask build(Task task) {
        return new TaskLogger((int) Math.round(task.getPeriod() * 60), repository, strategy);
    }

    public static class TaskLogger extends Monitor {
        private static final Logger LOGGER = LoggerFactory.getLogger(TaskLogger.class);

        public TaskLogger(int period, ReadingRepository repository, ReadingStrategy strategy) {
            super(period, repository, strategy);
        }

        @Override
        public TaskExecutionResult execute(Node node, TaskContext context) throws WioException {
            TaskExecutionResult nextRun = SchedulingUtils.secondsLater(period);
            Reading reading = new Reading(node.getName());
            for (Grove grove : node.getGroves()) {
                addData(grove, reading);
            }
            LOGGER.info("Reading of node \"" + node.getName() + "\": " + reading.toString());
            return nextRun;
        }
    }

}
