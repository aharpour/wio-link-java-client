package nl.openweb.iot.wio.domain.grove;

import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.db.GroveBean;
import nl.openweb.iot.wio.domain.Grove;
import nl.openweb.iot.wio.domain.Node;
import nl.openweb.iot.wio.domain.Type;
import nl.openweb.iot.wio.rest.NodeResource;

@Type("GenericDIn")
public class GroveGenericDigitalInput extends Grove {

    public GroveGenericDigitalInput(GroveBean groveBean, Node parent, NodeResource nodeResource) {
        super(groveBean, parent, nodeResource);
    }

    public int readInput() throws WioException {
        return toInteger(readProperty("input"));
    }

    public int risesSinceLastRead() throws WioException {
        return toInteger(readProperty("edge_rise_since_last_read", "rises"));
    }

    public int fallsSinceLastRead() throws WioException {
        return toInteger(readProperty("edge_fall_since_last_read", "falls"));
    }

}
