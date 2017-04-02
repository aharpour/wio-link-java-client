package nl.openweb.iot.dashboard.service;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import nl.openweb.iot.dashboard.domain.EventHandler;
import nl.openweb.iot.dashboard.domain.HandlerBean;
import nl.openweb.iot.dashboard.domain.Task;
import nl.openweb.iot.dashboard.domain.TaskHandler;
import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.scheduling.ScheduledTask;
import nl.openweb.iot.wio.scheduling.SchedulingService;
import nl.openweb.iot.wio.scheduling.SchedulingUtils;
import nl.openweb.iot.wio.scheduling.TaskEventHandler;

@Service
public class TaskService {

    private final static Logger LOG = LoggerFactory.getLogger(TaskService.class);

    private final ApplicationContext context;
    private final SchedulingService schedulingService;

    public TaskService(ApplicationContext context, SchedulingService schedulingService) {
        this.context = context;
        this.schedulingService = schedulingService;
    }

    public void terminateTask(String taskId) {
        schedulingService.terminateTask(taskId);
    }

    public String startTask(Task task) throws ClassNotFoundException, WioException {
        ScheduledTask taskHandler = getTaskHandler(task);
        SchedulingService.TaskBuilder builder =
            schedulingService.build(taskHandler, task.getNode().getNodeSn())
                .setKeepAwake(task.isKeepAwake())
                .setForceSleep(task.isForceSleep());
        if (task.getEventHandler() != null) {
            builder.setEventHandler(getEventHandler(task));
        }
        return task.getId() == null ? builder.build() : builder.build(task.getId());
    }

    private ScheduledTask getTaskHandler(Task task) throws ClassNotFoundException {
        ScheduledTask result = (n, c) -> SchedulingUtils.hoursLater(5);
        TaskHandler taskHandler = task.getTaskHandler();
        if (taskHandler != null && StringUtils.isNotBlank(taskHandler.getCode())) {
            result = createInstanceFromFactoryName(task, taskHandler, result, TaskHandlerFactory.class);
        }
        return result;
    }

    private TaskEventHandler getEventHandler(Task task) throws ClassNotFoundException {
        TaskEventHandler result = (e, n, c) -> {
        };
        EventHandler eventHandler = task.getEventHandler();
        if (eventHandler != null && StringUtils.isNotBlank(eventHandler.getCode())) {
            result = createInstanceFromFactoryName(task, eventHandler, result, EventHandlerFactory.class);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private <T> T createInstanceFromFactoryName(Task task, HandlerBean handlerBean, T defaultValue, Class<?> clazz) throws ClassNotFoundException {
        String factoryName = handlerBean.getCode();
        Class<?> factoryClass = Class.forName(factoryName);
        if (clazz.isAssignableFrom(factoryClass)) {
            HandlerFactory factory = (HandlerFactory) context.getBean(factoryClass);
            defaultValue = (T) factory.build(task);
        } else {
            LOG.error("the give class " + factoryClass.getName() + " is not a instance of " + clazz.getName());
        }
        return defaultValue;
    }
}
