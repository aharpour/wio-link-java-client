package nl.openweb.iot.wio.domain.grove;

import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.db.GroveBean;
import nl.openweb.iot.wio.domain.Grove;
import nl.openweb.iot.wio.domain.Node;
import nl.openweb.iot.wio.domain.Type;
import nl.openweb.iot.wio.rest.NodeResource;

@Type("GenericAIn")
public class GroveGenericAnalogInput extends Grove {

    public GroveGenericAnalogInput(GroveBean groveBean, Node parent, NodeResource nodeResource) {
        super(groveBean, parent, nodeResource);
    }

    public int readAnalog() throws WioException {
        return toInteger(readProperty("analog "));
    }

    public double readVoltage() throws WioException {
        return toDouble(readProperty("voltage", "volt"));
    }
}
