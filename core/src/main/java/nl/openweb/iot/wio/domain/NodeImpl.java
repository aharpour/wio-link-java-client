package nl.openweb.iot.wio.domain;

import java.util.*;
import java.util.function.BiConsumer;

import lombok.Getter;
import nl.openweb.iot.wio.NodeDecorator;
import nl.openweb.iot.wio.NodeService;
import nl.openweb.iot.wio.WebSocketService;
import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.db.GroveBean;
import nl.openweb.iot.wio.db.NodeBean;
import nl.openweb.iot.wio.rest.NodeResource;

@Getter
public class NodeImpl implements Node {

    public static final int FIVE_HOURS_IN_SEC = 18000;
    private final String name;
    private final String nodeKey;
    private final String nodeSn;
    private final String dataXServer;
    private final String board;
    private final List<Grove> groves;
    private final NodeResource nodeResource;
    private final WebSocketService webSocketService;
    private final NodeService nodeService;
    private BiConsumer<Map<String, String>, Node> eventHandler;

    public NodeImpl(NodeBean nodeBean, GroveFactory factory, NodeResource nodeResource, WebSocketService webSocketService, NodeService nodeService) throws WioException {
        this.nodeResource = nodeResource;
        name = nodeBean.getName();
        nodeKey = nodeBean.getNodeKey();
        nodeSn = nodeBean.getNodeSn();
        dataXServer = nodeBean.getDataXServer();
        board = nodeBean.getBoard();
        List<Grove> list = new ArrayList<>();
        for (GroveBean groveBean : nodeBean.getGroves()) {
            list.add(factory.createGrove(groveBean, this));
        }
        groves = Collections.unmodifiableList(list);
        this.webSocketService = webSocketService;
        this.nodeService = nodeService;
    }

    @Override
    public <T extends Grove> Optional<T> getGroveByName(String name) {
        return (Optional<T>) getGroves().stream().filter(g -> g.getName().equals(name)).<Optional<T>>findFirst();
    }

    @Override
    public <T extends Grove> Optional<T> getGroveByType(Class<T> clazz) {
        Optional<T> result = Optional.empty();
        for (Grove grove : groves) {
            if (clazz.isAssignableFrom(grove.getClass())) {
                result = Optional.of((T) grove);
                break;
            }
        }
        return result;
    }

    @Override
    public <T extends Grove> List<T> getGrovesByType(Class<T> clazz) {
        List<T> result = new ArrayList<>();
        for (Grove grove : groves) {
            if (clazz.isAssignableFrom(grove.getClass())) {
                result.add((T) grove);
            }
        }
        return result;
    }

    @Override
    public boolean isPassive() {
        boolean passive = true;
        List<Grove> groves = getGroves();
        for (Grove grove : groves) {
            if (!grove.isPassive()) {
                passive = false;
                break;
            }
        }
        return passive;
    }

    @Override
    public int getWarmUpTime() {
        int time = 0;
        for (Grove g : getGroves()) {
            time = Math.max(time, g.getWarmUpTime());
        }
        return time;
    }

    @Override
    public void sleep(int sec) throws WioException {
        if (sec > 0) {
            nodeResource.sleep(nodeKey, Math.min(sec, FIVE_HOURS_IN_SEC));
        }
    }

    @Override
    public void event(Map<String, String> map) {
        if (eventHandler != null) {
            eventHandler.accept(map, this);
        }
    }

    public void setEventHandler(BiConsumer<Map<String, String>, Node> eventHandler) {
        this.eventHandler = eventHandler;
        this.webSocketService.connect(new NodeDecorator(nodeService, this.getNodeSn()) );
    }

    /**
     * This method is very inefficient. Try to avoid using it.
     *
     * @return true if online otherwise false
     */
    @Override
    public boolean isOnline() {
        boolean result = false;
        try {
            nodeResource.getNodeInfo(nodeKey);
            result = true;
        } catch (WioException e) {
            // Ignore
        }
        return result;
    }
}
