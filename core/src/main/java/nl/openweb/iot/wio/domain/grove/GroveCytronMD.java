package nl.openweb.iot.wio.domain.grove;

import lombok.AllArgsConstructor;
import lombok.Data;
import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.db.GroveBean;
import nl.openweb.iot.wio.domain.Grove;
import nl.openweb.iot.wio.domain.Node;
import nl.openweb.iot.wio.domain.Type;
import nl.openweb.iot.wio.rest.NodeResource;

import java.util.Map;

@Type("CytronMD13SUART0")
public class GroveCytronMD extends Grove {

    public GroveCytronMD(GroveBean groveBean, Node parent, NodeResource nodeResource) {
        super(groveBean, parent, nodeResource);
    }

    public int readDirection() throws WioException {
        return toInteger(readProperty("direction"));
    }

    public double readSpeed() throws WioException {
        return toDouble(readProperty("speed"));
    }

    public SpeedAndDirection readSpeedAndDirection() throws WioException {
        Map<String, String> map = readPropertyAsMap("speed_dir");
        return new SpeedAndDirection(toInteger(map.get("direction")), toDouble(map.get("speed")));
    }

    public boolean writeSpeed(double speed) throws WioException {
        return writeProperty("speed", Double.toString(speed));
    }

    public boolean writeDirection(int direction) throws WioException {
        return writeProperty("direction", Integer.toString(direction));
    }

    public boolean writeSpeedAndDirection(double speed, int direction) throws WioException {
        return writeProperty("speed_dir", Integer.toString(direction), Integer.toString(direction));
    }

    @Data
    @AllArgsConstructor
    public static class SpeedAndDirection {
        private int direction;
        private double speed;
    }


}
