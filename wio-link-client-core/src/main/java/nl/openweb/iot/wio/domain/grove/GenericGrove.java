package nl.openweb.iot.wio.domain.grove;

import lombok.Getter;
import nl.openweb.iot.wio.db.GroveBean;
import nl.openweb.iot.wio.domain.Grove;
import nl.openweb.iot.wio.domain.Node;
import nl.openweb.iot.wio.rest.NodeResource;

public class GenericGrove implements Grove {
    @Getter
    protected final String name, type;
    @Getter
    protected Node parent;

    protected final NodeResource nodeResource;

    public GenericGrove(GroveBean groveBean, Node parent, NodeResource nodeResource) {
        this.name = groveBean.getName();
        this.type = groveBean.getType();
        this.parent = parent;
        this.nodeResource = nodeResource;
    }

    @Override
    public boolean isPassive() {
        return true;
    }
}
