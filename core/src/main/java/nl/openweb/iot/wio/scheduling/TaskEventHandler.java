package nl.openweb.iot.wio.scheduling;

import java.util.Map;

import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.domain.Node;

@FunctionalInterface
public interface TaskEventHandler {

    void handle(Map<String, String> event, Node node, TaskContext context) throws WioException;
}
