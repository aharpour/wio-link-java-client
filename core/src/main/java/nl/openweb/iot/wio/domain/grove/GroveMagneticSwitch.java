package nl.openweb.iot.wio.domain.grove;


import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.db.GroveBean;
import nl.openweb.iot.wio.domain.Grove;
import nl.openweb.iot.wio.domain.Node;
import nl.openweb.iot.wio.domain.Type;
import nl.openweb.iot.wio.rest.NodeResource;

@Type
public class GroveMagneticSwitch extends Grove {

    public GroveMagneticSwitch(GroveBean groveBean, Node parent, NodeResource nodeResource) {
        super(groveBean, parent, nodeResource);
    }

    public Integer readApproach() throws WioException {
        return toInteger(readProperty("approach", "mag_approach"));
    }
}
