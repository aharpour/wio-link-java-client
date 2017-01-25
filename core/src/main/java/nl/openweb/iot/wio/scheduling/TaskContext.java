package nl.openweb.iot.wio.scheduling;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;


public class TaskContext {

    private Map<String, Object> attributes = new ConcurrentHashMap<>();
    private final ApplicationContext applicationContext;

    TaskContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return applicationContext.getBean(requiredType);
    }

    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return applicationContext.getBean(name, requiredType);
    }

    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return applicationContext.getBeansOfType(type);
    }

    public Object setAttribute(String key, Object value) {
        return attributes.put(key, value);
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    public Object getAttributeOrDefault(String key, Object defaultValue) {
        return attributes.getOrDefault(key, defaultValue);
    }
}
