package nl.openweb.iot.wio.domain.grove;

import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.db.GroveBean;
import nl.openweb.iot.wio.domain.Node;
import nl.openweb.iot.wio.domain.Type;
import nl.openweb.iot.wio.rest.NodeResource;

@Type("GroveMoisture")
public class GroveMoisture extends GenericGrove {

    public GroveMoisture(GroveBean groveBean, Node parent, NodeResource nodeResource) {
        super(groveBean, parent, nodeResource);
    }

    public int readMoisture() throws WioException {
        String value = this.nodeResource.readProperty(parent.getNodeKey(), name, "moisture").get("moisture");
        return Integer.parseInt(value);
    }
}
