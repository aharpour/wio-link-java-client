package nl.openweb.iot.dashboard.service.script;

import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.domain.Node;
import nl.openweb.iot.wio.scheduling.ScheduledTask;
import nl.openweb.iot.wio.scheduling.TaskContext;

public class GroovyScriptTaskHandler implements ScheduledTask {
    private ScheduledTask task;

    GroovyScriptTaskHandler(AbstractGroovyTaskHandler task) {
        this.task = task;
    }

    @Override
    public TaskExecutionResult execute(Node node, TaskContext context) throws WioException {
        GroovySandboxFilter sandboxFilter = new GroovySandboxFilter();
        try {
            sandboxFilter.register();
            return task.execute(node, new ScriptContextWrapper(context));
        } finally {
            sandboxFilter.unregister();
        }
    }
}
