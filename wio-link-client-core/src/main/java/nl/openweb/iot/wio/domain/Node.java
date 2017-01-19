package nl.openweb.iot.wio.domain;

import java.util.List;
import java.util.Optional;

public interface Node {

    <T extends Grove> Optional<T> getGroveByName(String name);

    <T extends Grove> Optional<T> getGroveByType(Class<T> clazz);

    <T extends Grove> List<T> getGrovesByType(Class<T> clazz);

    String getName();

    String getNodeKey();

    String getNodeSn();

    String getDataXServer();

    String getBoard();

    boolean getOnline();

    List<Grove> getGroves();
}
