package nl.openweb.iot.wio.scheduling;

import java.util.Map;

import org.springframework.beans.BeansException;

import nl.openweb.iot.wio.WioException;

public interface TaskContext {
    <T> T getBean(Class<T> requiredType) throws BeansException;

    <T> T getBean(String name, Class<T> requiredType) throws BeansException;

    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

    Object setAttribute(String key, Object value);

    Object getAttribute(String key);

    Object getAttributeOrDefault(String key, Object defaultValue);

    void sendEvent(String nodeName, Map<String, String> event) throws WioException;

    void sendEventBySnId(String SnId, Map<String, String> event) throws WioException;
}
