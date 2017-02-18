package nl.openweb.iot.dashboard.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import nl.openweb.iot.dashboard.domain.Task;
import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.domain.Node;
import nl.openweb.iot.wio.scheduling.TaskContext;
import nl.openweb.iot.wio.scheduling.TaskEventHandler;

@Service
public class LoggerEventHandlerFactory implements EventHandlerFactory {
    @Override
    public TaskEventHandler build(Task task) {
        return new LogEventHandler();
    }

    public static class LogEventHandler implements TaskEventHandler {
        private static final Logger LOGGER = LoggerFactory.getLogger(LogEventHandler.class);

        @Override
        public void handle(Map<String, String> map, Node node, TaskContext context) throws WioException {
            LOGGER.info("Event on node \"" + node.getName() + "\": " + map.toString());
        }
    }
}
