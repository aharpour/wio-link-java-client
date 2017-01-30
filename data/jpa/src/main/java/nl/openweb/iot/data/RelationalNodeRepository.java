package nl.openweb.iot.data;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import nl.openweb.iot.wio.db.NodeBean;
import nl.openweb.iot.wio.repository.NodeRepository;

public class RelationalNodeRepository implements NodeRepository<String> {

    private JpaNodeRepository jpaNodeRepository;

    @Autowired
    public RelationalNodeRepository(JpaNodeRepository jpaNodeRepository) {
        this.jpaNodeRepository = jpaNodeRepository;
    }

    @Override
    public List<NodeBean> findAll(Iterable<String> longs) {
        List<JpaNodeBean> all = jpaNodeRepository.findAll(longs);
        return jpaListToBeanList(all);
    }

    @Override
    public long count() {
        return jpaNodeRepository.count();
    }

    @Override
    public NodeBean save(NodeBean entity) {
        JpaNodeBean saved = jpaNodeRepository.save(new JpaNodeBean(entity));
        return saved.toNodeBean();
    }

    @Override
    public <S extends NodeBean> Iterable<S> save(Iterable<S> entities) {
        List<S> result = new ArrayList<S>();
        for (NodeBean entity : entities) {
            S save = (S) jpaNodeRepository.save(new JpaNodeBean(entity)).toNodeBean();
            result.add(save);
        }
        return result;
    }

    @Override
    public NodeBean findOne(String id) {
        JpaNodeBean jpaNode = jpaNodeRepository.findOne(id);
        return toNodeBean(jpaNode);
    }

    @Override
    public boolean exists(String id) {
        return jpaNodeRepository.exists(id);
    }

    @Override
    public Iterable<NodeBean> findAll() {
        return null;
    }

    @Override
    public void delete(String id) {
        jpaNodeRepository.delete(id);
    }

    @Override
    public void delete(NodeBean entity) {
        jpaNodeRepository.delete(new JpaNodeBean(entity));
    }

    @Override
    public void delete(Iterable<? extends NodeBean> entities) {
        for (NodeBean entity : entities) {
            jpaNodeRepository.delete(new JpaNodeBean(entity));
        }

    }

    @Override
    public void deleteAll() {
        jpaNodeRepository.deleteAll();
    }

    @Override
    public NodeBean findOneByName(String name) {
        return toNodeBean(jpaNodeRepository.findOneByName(name));
    }

    @Override
    public NodeBean findOneNodeSn(String nodeSn) {
        return toNodeBean(jpaNodeRepository.findOne(nodeSn));
    }

    private NodeBean toNodeBean(JpaNodeBean jpaNodeBean) {
        return jpaNodeBean != null ? jpaNodeBean.toNodeBean() : null;
    }

    private List<NodeBean> jpaListToBeanList(List<JpaNodeBean> all) {
        List<NodeBean> result = new ArrayList<>();
        for (JpaNodeBean jpaNodeBean : all) {
            result.add(jpaNodeBean.toNodeBean());
        }
        return result;
    }
}
