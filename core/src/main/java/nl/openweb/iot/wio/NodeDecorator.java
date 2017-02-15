package nl.openweb.iot.wio;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

import nl.openweb.iot.wio.domain.Grove;
import nl.openweb.iot.wio.domain.Node;

public class NodeDecorator implements Node {

    private final NodeService nodeService;
    private final String nodeSn;

    public NodeDecorator(NodeService nodeService, String nodeSn) {
        this.nodeService = nodeService;
        this.nodeSn = nodeSn;
    }

    private Node getNode() {
        try {
            return nodeService.findOriginalNodeBySnId(nodeSn);
        } catch (WioException e) {
            throw new RuntimeException("An unexpected WioException was thrown.", e);
        }
    }

    @Override
    public <T extends Grove> Optional<T> getGroveByName(String name) {
        return getNode().getGroveByName(name);
    }


    @Override
    public <T extends Grove> Optional<T> getGroveByType(Class<T> clazz) {
        return getNode().getGroveByType(clazz);
    }

    @Override
    public <T extends Grove> List<T> getGrovesByType(Class<T> clazz) {
        return getNode().getGrovesByType(clazz);
    }

    @Override
    public void event(Map<String, String> map) {
        getNode().event(map);
    }

    @Override
    public void setEventHandler(BiConsumer<Map<String, String>, Node> eventHandler) {
        getNode().setEventHandler(eventHandler);
    }

    @Override
    public String getName() {
        return getNode().getName();
    }

    @Override
    public String getNodeKey() {
        return getNode().getNodeKey();
    }

    @Override
    public String getNodeSn() {
        return getNode().getNodeSn();
    }

    @Override
    public String getDataXServer() {
        return getNode().getDataXServer();
    }

    @Override
    public String getBoard() {
        return getNode().getBoard();
    }

    @Override
    public boolean isOnline() {
        return getNode().isOnline();
    }

    @Override
    public boolean isPassive() {
        return getNode().isPassive();
    }

    @Override
    public void sleep(int sec) throws WioException {
        getNode().sleep(sec);
    }

    @Override
    public List<Grove> getGroves() {
        return getNode().getGroves();
    }

    @Override
    public int getWarmUpTime() {
        return getNode().getWarmUpTime();
    }
}
