package nl.openweb.iot.dashboard.service;

import nl.openweb.iot.dashboard.domain.Task;
import nl.openweb.iot.wio.scheduling.ScheduledTask;

public interface TaskHandlerFactory {

    ScheduledTask build(Task task);
}
