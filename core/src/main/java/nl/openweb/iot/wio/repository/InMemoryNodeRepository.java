package nl.openweb.iot.wio.repository;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.lang.StringUtils;

import nl.openweb.iot.wio.db.NodeBean;
import nl.openweb.iot.wio.rest.NodesResource;

public class InMemoryNodeRepository implements NodeRepository<String> {

    private Map<String, NodeBean> data = new ConcurrentHashMap<>();

    private NodesResource nodesResource;

    public NodeBean save(NodeBean entity) {
        if (StringUtils.isNotBlank(entity.getNodeSn())) {
            data.put(entity.getNodeSn(), entity);
        } else {
            throw new IllegalArgumentException("nodeSn is required.");
        }
        return entity;
    }

    public <S extends NodeBean> Iterable<S> save(Iterable<S> entities) {
        throw new UnsupportedOperationException();
    }

    public NodeBean findOne(String nodeSn) {
        return data.get(nodeSn);
    }

    public boolean exists(String nodeSn) {
        return data.containsKey(nodeSn);
    }

    public Iterable<NodeBean> findAll() {
        return data.values();
    }

    public Iterable<NodeBean> findAll(Iterable<String> nodeSns) {
        return StreamSupport.stream(nodeSns.spliterator(), false)
                .map(data::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public long count() {
        return data.size();
    }

    public void delete(String nodeSn) {
        data.remove(nodeSn);
    }

    public void delete(NodeBean entity) {
        if (StringUtils.isNotBlank(entity.getNodeSn()) && entity.getGroves() != null) {
            delete(entity.getNodeSn());
        } else {
            throw new IllegalArgumentException("nodeSn is required.");
        }
    }

    public void delete(Iterable<? extends NodeBean> entities) {
        entities.forEach(this::delete);
    }

    public void deleteAll() {
        data = new ConcurrentHashMap<>();
    }

    @Override
    public NodeBean findOneByName(String name) {
        NodeBean result = null;
        for (NodeBean bean : data.values()) {
            if (bean.getName().equals(name)) {
                result = bean;
                break;
            }
        }
        return result;
    }

    @Override
    public NodeBean findOneNodeSn(String nodeSn) {
        return data.get(nodeSn);
    }
}
