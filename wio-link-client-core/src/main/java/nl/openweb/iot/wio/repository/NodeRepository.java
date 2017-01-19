package nl.openweb.iot.wio.repository;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;

import nl.openweb.iot.wio.db.NodeBean;

public interface NodeRepository<ID extends Serializable> extends CrudRepository<NodeBean, ID> {

    NodeBean findOneByName(String name);

    NodeBean findOneNodeSn(String nodeSn);
}
