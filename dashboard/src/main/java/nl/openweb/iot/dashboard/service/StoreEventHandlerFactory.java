package nl.openweb.iot.dashboard.service;

import org.springframework.stereotype.Service;

import nl.openweb.iot.dashboard.domain.Task;
import nl.openweb.iot.monitor.StoreEventHandler;
import nl.openweb.iot.wio.scheduling.TaskEventHandler;

@Service
public class StoreEventHandlerFactory implements EventHandlerFactory {

    @Override
    public TaskEventHandler build(Task task) {
        return new StoreEventHandler();
    }
}
