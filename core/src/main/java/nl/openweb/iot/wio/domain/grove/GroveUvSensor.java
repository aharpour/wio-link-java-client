package nl.openweb.iot.wio.domain.grove;

import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.db.GroveBean;
import nl.openweb.iot.wio.domain.Grove;
import nl.openweb.iot.wio.domain.Node;
import nl.openweb.iot.wio.domain.Type;
import nl.openweb.iot.wio.rest.NodeResource;

@Type("GroveSI114X")
public class GroveUvSensor extends Grove {

    public GroveUvSensor(GroveBean groveBean, Node parent, NodeResource nodeResource) {
        super(groveBean, parent, nodeResource);
    }

    public double readVisibleLight() throws WioException {
        return toDouble(readProperty("visiblelight", "VL"));
    }

    public double readIR() throws WioException {
        return toDouble(readProperty("IR"));
    }

    public double readUV() throws WioException {
        return toDouble(readProperty("UV"));
    }
}
