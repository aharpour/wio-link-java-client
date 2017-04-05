package nl.openweb.iot.dashboard.service.script;

import java.util.Map;

import jdk.nashorn.api.scripting.JSObject;
import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.domain.Node;
import nl.openweb.iot.wio.scheduling.TaskContext;
import nl.openweb.iot.wio.scheduling.TaskEventHandler;

public class JsEventHandler implements TaskEventHandler {


    private final JSObject jsObject;
    private final JSObject handle;

    public JsEventHandler(JSObject jsObject) {
        this.jsObject = jsObject;
        handle = (JSObject) jsObject.getMember("handle");
    }

    @Override
    public void handle(Map<String, String> event, Node node, TaskContext context) throws WioException {
        handle.call(jsObject, event, node, context);
    }
}
