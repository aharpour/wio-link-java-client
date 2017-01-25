package nl.openweb.iot.wio.domain;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

import nl.openweb.iot.wio.WioException;

public interface Node {

    <T extends Grove> Optional<T> getGroveByName(String name);

    <T extends Grove> Optional<T> getGroveByType(Class<T> clazz);

    <T extends Grove> List<T> getGrovesByType(Class<T> clazz);

    void event(Map<String, String> map);

    void setEventHandler(BiConsumer<Map<String, String>, Node> eventHandler);

    String getName();

    String getNodeKey();

    String getNodeSn();

    String getDataXServer();

    String getBoard();

    boolean isOnline();

    boolean isPassive();

    void sleep(int sec) throws WioException;

    List<Grove> getGroves();

    int getWarmUpTime();
}
