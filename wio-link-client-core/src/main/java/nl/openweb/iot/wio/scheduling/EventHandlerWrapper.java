package nl.openweb.iot.wio.scheduling;


import java.util.Map;
import java.util.function.BiConsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.domain.Node;

class EventHandlerWrapper implements BiConsumer<Map<String, String>, Node> {
    private static final Logger LOG = LoggerFactory.getLogger(EventHandlerWrapper.class);

    private final TaskContext taskContext;
    private final TaskEventHandler eventHandler;

    EventHandlerWrapper(TaskContext taskContext, TaskEventHandler eventHandler) {
        this.taskContext = taskContext;
        this.eventHandler = eventHandler;
    }

    @Override
    public void accept(Map<String, String> event, Node node) {
        try {
            eventHandler.handle(event, node, taskContext);
        } catch (WioException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
