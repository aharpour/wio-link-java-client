package nl.openweb.iot.dashboard.service.script;

import java.util.Map;

import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.domain.Node;
import nl.openweb.iot.wio.scheduling.TaskContext;
import nl.openweb.iot.wio.scheduling.TaskEventHandler;

public class GroovyScriptEventHandler implements TaskEventHandler {

    private final TaskEventHandler eventHandler;


    public GroovyScriptEventHandler(TaskEventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    @Override
    public void handle(Map<String, String> event, Node node, TaskContext context) throws WioException {

        GroovySandboxFilter sandboxFilter = new GroovySandboxFilter();
        try {
            sandboxFilter.register();
            eventHandler.handle(event, node, new ScriptContextWrapper(context));
        } finally {
            sandboxFilter.unregister();
        }
    }
}
