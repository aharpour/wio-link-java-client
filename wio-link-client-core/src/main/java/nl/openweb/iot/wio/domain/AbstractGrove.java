package nl.openweb.iot.wio.domain;

import lombok.Getter;
import nl.openweb.iot.wio.db.GroveBean;
import nl.openweb.iot.wio.rest.NodeResource;

//FIXME please
public class AbstractGrove implements Grove {
    @Getter
    private final String name, type;
    @Getter
    private Node parent;

    private final NodeResource nodeResource;

    public AbstractGrove(GroveBean groveBean, Node parent, NodeResource nodeResource) {
        this.name = groveBean.getName();
        this.type = groveBean.getType();
        this.parent = parent;
        this.nodeResource = nodeResource;
    }
}
