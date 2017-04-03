package nl.openweb.iot.dashboard.service.script;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeansException;

import nl.openweb.iot.monitor.ReadingStrategy;
import nl.openweb.iot.monitor.repository.ReadingRepository;
import nl.openweb.iot.wio.NodeService;
import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.scheduling.TaskContext;

public class ScriptContextWrapper implements TaskContext {
    private TaskContext context;
    private static final Set<Class<?>> ALLOWED_SERVICES;

    static {
        HashSet<Class<?>> allowed = new HashSet<>();
        allowed.add(NodeService.class);
        allowed.add(ReadingRepository.class);
        allowed.add(ReadingStrategy.class);
        ALLOWED_SERVICES = Collections.unmodifiableSet(allowed);
    }

    public ScriptContextWrapper(TaskContext context) {
        this.context = context;
    }

    public <T> T getBean(Class<T> requiredType) throws BeansException {
        if (!ALLOWED_SERVICES.contains(requiredType)) {
            throw new SecurityException("Scripts are not allowed to access services of type " + requiredType);
        }
        return context.getBean(requiredType);
    }

    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        if (!ALLOWED_SERVICES.contains(requiredType)) {
            throw new SecurityException("Scripts are not allowed to access services of type " + requiredType);
        }
        return context.getBean(name, requiredType);
    }

    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        if (!ALLOWED_SERVICES.contains(type)) {
            throw new SecurityException("Scripts are not allowed to access services of type " + type);
        }
        return context.getBeansOfType(type);
    }

    public Object setAttribute(String key, Object value) {
        return context.setAttribute(key, value);
    }

    public Object getAttribute(String key) {
        return context.getAttribute(key);
    }

    public Object getAttributeOrDefault(String key, Object defaultValue) {
        return context.getAttributeOrDefault(key, defaultValue);
    }

    public void sendEvent(String nodeName, Map<String, String> event) throws WioException {
        context.sendEvent(nodeName, event);
    }

    public void sendEventBySnId(String SnId, Map<String, String> event) throws WioException {
        context.sendEventBySnId(SnId, event);
    }
}
