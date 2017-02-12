package nl.openweb.iot.dashboard.service;

import nl.openweb.iot.dashboard.domain.Task;
import nl.openweb.iot.wio.scheduling.TaskEventHandler;

public interface EventHandlerFactory {

    TaskEventHandler build(Task task);
}
