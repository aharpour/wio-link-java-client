package nl.openweb.iot.wio.domain.grove;

import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.db.GroveBean;
import nl.openweb.iot.wio.domain.Grove;
import nl.openweb.iot.wio.domain.Node;
import nl.openweb.iot.wio.domain.Type;
import nl.openweb.iot.wio.rest.NodeResource;

@Type("GroveAirquality")
public class GroveAirQuality extends Grove {

    public GroveAirQuality(GroveBean groveBean, Node parent, NodeResource nodeResource) {
        super(groveBean, parent, nodeResource);
    }

    public Integer readQuality() throws WioException {
        return toInteger(readProperty("quality"));
    }
}
