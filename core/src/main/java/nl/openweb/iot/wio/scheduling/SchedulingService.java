package nl.openweb.iot.wio.scheduling;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import nl.openweb.iot.wio.NodeService;
import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.WioSettings;
import nl.openweb.iot.wio.domain.Node;

@Service
public class SchedulingService {

    private final TaskScheduler taskScheduler;
    private final NodeService nodeService;
    private final ApplicationContext applicationContext;
    private final WioSettings settings;
    private final Map<String, TaskWrapper> registry = new ConcurrentHashMap<>();

    public SchedulingService(TaskScheduler taskScheduler, NodeService nodeService, ApplicationContext applicationContext, WioSettings wioSettings) {
        this.taskScheduler = taskScheduler;
        this.nodeService = nodeService;
        this.applicationContext = applicationContext;
        this.settings = wioSettings;
    }

    public void terminateTask(String taskId) {
        TaskWrapper taskWrapper = registry.get(taskId);
        taskWrapper.setTerminate(true);
        registry.remove(taskId);
    }

    void schedule(TaskWrapper task, Date date) {
        taskScheduler.schedule(task, date);
    }

    public TaskBuilder build(String nodeName, ScheduledTask task) throws WioException {
        Node node = nodeService.findNodeByName(nodeName);
        return new TaskBuilder(node, task);
    }

    public TaskBuilder build(ScheduledTask task, String snId) throws WioException {
        Node node = nodeService.findNodeBySnId(snId);
        return new TaskBuilder(node, task);
    }

    public class TaskBuilder {
        private boolean keepAwake = false;
        private boolean forceSleep = false;
        private EventHandlerWrapper eventHandler = null;
        private TaskContext context = new TaskContext(applicationContext);

        private final Node node;
        private final ScheduledTask task;

        private TaskBuilder(Node node, ScheduledTask task) {
            this.node = node;
            this.task = task;
        }

        public TaskBuilder setEventHandler(TaskEventHandler eventHandler) {
            this.eventHandler = new EventHandlerWrapper(context, eventHandler);
            return this;
        }

        public TaskBuilder setKeepAwake(boolean keepAwake) {
            this.keepAwake = keepAwake;
            return this;
        }

        public TaskBuilder setForceSleep(boolean forceSleep) {
            this.forceSleep = forceSleep;
            return this;
        }

        public String build() {
            TaskWrapper taskWrapper = new TaskWrapper(node, SchedulingService.this, context, task, settings);
            String taskId = taskWrapper.getTaskId();
            return initialTaskWrapper(taskWrapper, taskId);
        }



        public String build(String taskId) {
            if (registry.containsKey(taskId)) {
                throw new IllegalArgumentException("A task with \"" + taskId + "\" task id already exists.");
            }
            TaskWrapper taskWrapper = new TaskWrapper(taskId, node, SchedulingService.this, context, task, settings);
            return initialTaskWrapper(taskWrapper, taskId);
        }

        private String initialTaskWrapper(TaskWrapper taskWrapper, String taskId) {
            taskWrapper.setEventHandler(eventHandler);
            taskWrapper.setForceSleep(forceSleep);
            taskWrapper.setKeepAwake(keepAwake);
            taskWrapper.init();
            registry.put(taskId, taskWrapper);
            taskWrapper.run();
            return taskId;
        }
    }
}
