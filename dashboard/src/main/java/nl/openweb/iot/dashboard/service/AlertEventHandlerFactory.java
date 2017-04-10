package nl.openweb.iot.dashboard.service;

import org.springframework.stereotype.Service;

import nl.openweb.iot.dashboard.domain.Task;
import nl.openweb.iot.wio.scheduling.TaskEventHandler;

@Service
public class AlertEventHandlerFactory implements EventHandlerFactory {
    @Override
    public TaskEventHandler build(Task task) {
        return (e, n, c) -> {
            NotificationService bean = c.getBean(NotificationService.class);
            bean.notifyAllUsers("An event has been triggered.", e.toString(), NotificationService.Type.EMAIL);
        };
    }
}
