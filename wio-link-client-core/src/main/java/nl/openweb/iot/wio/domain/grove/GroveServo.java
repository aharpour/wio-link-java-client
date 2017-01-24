package nl.openweb.iot.wio.domain.grove;

import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.db.GroveBean;
import nl.openweb.iot.wio.domain.Grove;
import nl.openweb.iot.wio.domain.Node;
import nl.openweb.iot.wio.domain.Type;
import nl.openweb.iot.wio.rest.NodeResource;

@Type
public class GroveServo extends Grove {
    public GroveServo(GroveBean groveBean, Node parent, NodeResource nodeResource) {
        super(groveBean, parent, nodeResource);
    }
    public Integer readAngle() throws WioException {
        return toInteger(readProperty("angle", "degree"));
    }

    public boolean writeAngle(int degree) throws WioException {
        return writeProperty("angle", Integer.toString(degree));
    }

    public boolean writeAngle(int degree, int seconds) throws WioException {
        return writeProperty("angle", Integer.toString(degree), Integer.toString(seconds));
    }

}
