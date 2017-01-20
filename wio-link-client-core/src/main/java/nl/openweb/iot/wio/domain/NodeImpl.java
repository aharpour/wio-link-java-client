package nl.openweb.iot.wio.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.db.GroveBean;
import nl.openweb.iot.wio.db.NodeBean;
import nl.openweb.iot.wio.rest.NodeResource;

@Getter
public class NodeImpl implements Node {

    private final String name;
    private final String nodeKey;
    private final String nodeSn;
    private final String dataXServer;
    private final String board;
    private final List<Grove> groves;
    private final NodeResource nodeResource;

    public NodeImpl(NodeBean nodeBean, GroveFactory factory, NodeResource nodeResource) throws WioException {
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
