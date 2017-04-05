package nl.openweb.iot.dashboard.service.script;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jdk.nashorn.api.scripting.JSObject;
import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.domain.Node;
import nl.openweb.iot.wio.scheduling.ScheduledTask;
import nl.openweb.iot.wio.scheduling.TaskContext;

public class JsTaskHandler implements ScheduledTask {

    public static Logger LOG = LoggerFactory.getLogger(JsTaskHandler.class);

    private final JSObject jsObject;
    private final JSObject execute;

    public JsTaskHandler(JSObject jsObject) {
        this.jsObject = jsObject;
        execute = (JSObject) jsObject.getMember("execute");
    }

    @Override
    public TaskExecutionResult execute(Node node, TaskContext context) throws WioException {

        return (TaskExecutionResult) execute.call(jsObject, node, context);
    }
}
