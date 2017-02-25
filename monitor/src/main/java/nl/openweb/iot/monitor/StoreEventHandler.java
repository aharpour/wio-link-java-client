package nl.openweb.iot.monitor;

import java.util.Map;

import nl.openweb.iot.monitor.domain.Event;
import nl.openweb.iot.monitor.repository.EventRepository;
import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.domain.Node;
import nl.openweb.iot.wio.scheduling.TaskContext;
import nl.openweb.iot.wio.scheduling.TaskEventHandler;

public class StoreEventHandler implements TaskEventHandler {
    @Override
    public void handle(Map<String, String> event, Node node, TaskContext context) throws WioException {
        EventRepository eventRepository = context.getBean(EventRepository.class);
        eventRepository.save(new Event(event, node));
    }
}
