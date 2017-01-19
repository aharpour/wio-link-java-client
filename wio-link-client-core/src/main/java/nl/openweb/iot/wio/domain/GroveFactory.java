package nl.openweb.iot.wio.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.openweb.iot.wio.db.GroveBean;
import nl.openweb.iot.wio.rest.NodeResource;

@Service
public class GroveFactory {

    private NodeResource nodeResource;

    @Autowired
    public GroveFactory(NodeResource nodeResource) {
        this.nodeResource = nodeResource;
    }

    public Grove createGrove(GroveBean groveBean, Node parent) {
        //FIXME please
        return new AbstractGrove(groveBean, parent, nodeResource);
    }
}
