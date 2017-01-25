package nl.openweb.iot.wio.domain.grove;

import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.db.GroveBean;
import nl.openweb.iot.wio.domain.Grove;
import nl.openweb.iot.wio.domain.Node;
import nl.openweb.iot.wio.domain.Type;
import nl.openweb.iot.wio.rest.NodeResource;

@Type
public class GroveUltraRanger extends Grove {
    public GroveUltraRanger(GroveBean groveBean, Node parent, NodeResource nodeResource) {
        super(groveBean, parent, nodeResource);
    }

    public Double readRangeInCm() throws WioException {
        return toDouble(readProperty("range_in_cm", "range_cm"));
    }

    public Double readRangeInInch() throws WioException {
        return toDouble(readProperty("range_in_inch", "range_inch"));
    }
}
