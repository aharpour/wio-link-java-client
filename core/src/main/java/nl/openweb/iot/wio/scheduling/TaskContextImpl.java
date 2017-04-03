package nl.openweb.iot.wio.scheduling;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import nl.openweb.iot.wio.NodeService;
import nl.openweb.iot.wio.WioException;


public class TaskContextImpl implements TaskContext {

    private Map<String, Object> attributes = new ConcurrentHashMap<>();
    private final ApplicationContext applicationContext;
    private final NodeService nodeService;

    TaskContextImpl(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.nodeService = applicationContext.getBean(NodeService.class);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return applicationContext.getBean(requiredType);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return applicationContext.getBean(name, requiredType);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return applicationContext.getBeansOfType(type);
    }

    @Override
    public Object setAttribute(String key, Object value) {
        return attributes.put(key, value);
    }

    @Override
    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    @Override
    public Object getAttributeOrDefault(String key, Object defaultValue) {
        return attributes.getOrDefault(key, defaultValue);
    }

    @Override
    public void sendEvent(String nodeName, Map<String, String> event) throws WioException {
        nodeService.findNodeByName(nodeName).event(event);
    }

    @Override
    public void sendEventBySnId(String SnId, Map<String, String> event) throws WioException {
        nodeService.findNodeBySnId(SnId).event(event);
    }
}
