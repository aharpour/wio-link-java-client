package nl.openweb.iot.data;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import nl.openweb.iot.wio.db.NodeBean;
import nl.openweb.iot.wio.repository.NodeRepository;

public class ENodeRepository implements NodeRepository<String> {

    private ElasticNodeRepository elasticNodeRepository;

    @Autowired
    public ENodeRepository(ElasticNodeRepository elasticNodeRepository) {
        this.elasticNodeRepository = elasticNodeRepository;
    }

    @Override
    public List<NodeBean> findAll(Iterable<String> longs) {
        return elasticListToBeanList(elasticNodeRepository.findAll(longs));
    }

    @Override
    public long count() {
        return elasticNodeRepository.count();
    }

    @Override
    public NodeBean save(NodeBean entity) {
        ElasticNodeBean saved = elasticNodeRepository.save(new ElasticNodeBean(entity));
        return saved.toNodeBean();
    }

    @Override
    public <S extends NodeBean> Iterable<S> save(Iterable<S> entities) {
        List<S> result = new ArrayList<S>();
        for (NodeBean entity : entities) {
            S save = (S) elasticNodeRepository.save(new ElasticNodeBean(entity)).toNodeBean();
            result.add(save);
        }
        return result;
    }

    @Override
    public NodeBean findOne(String id) {
        return toNodeBean(elasticNodeRepository.findOne(id));
    }

    @Override
    public boolean exists(String id) {
        return elasticNodeRepository.exists(id);
    }

    @Override
    public Iterable<NodeBean> findAll() {
        return null;
    }

    @Override
    public void delete(String id) {
        elasticNodeRepository.delete(id);
    }

    @Override
    public void delete(NodeBean entity) {
        elasticNodeRepository.delete(new ElasticNodeBean(entity));
    }

    @Override
    public void delete(Iterable<? extends NodeBean> entities) {
        for (NodeBean entity : entities) {
            elasticNodeRepository.delete(new ElasticNodeBean(entity));
        }

    }

    @Override
    public void deleteAll() {
        elasticNodeRepository.deleteAll();
    }

    @Override
    public NodeBean findOneByName(String name) {
        return toNodeBean(elasticNodeRepository.findOneByName(name));
    }

    @Override
    public NodeBean findOneNodeSn(String nodeSn) {
        return toNodeBean(elasticNodeRepository.findOne(nodeSn));
    }

    private NodeBean toNodeBean(ElasticNodeBean elasticNodeBean) {
        return elasticNodeBean != null ? elasticNodeBean.toNodeBean() : null;
    }

    private List<NodeBean> elasticListToBeanList(Iterable<ElasticNodeBean> all) {
        List<NodeBean> result = new ArrayList<>();
        for (ElasticNodeBean elasticNodeBean : all) {
            result.add(elasticNodeBean.toNodeBean());
        }
        return result;
    }
}
