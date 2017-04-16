package nl.openweb.iot.wio.domain.grove;

import lombok.Data;
import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.db.GroveBean;
import nl.openweb.iot.wio.domain.Grove;
import nl.openweb.iot.wio.domain.Node;
import nl.openweb.iot.wio.domain.Type;
import nl.openweb.iot.wio.rest.NodeResource;

import java.util.Map;

@Type("GroveGyroITG3200")
public class GroveGyro extends Grove {

    public GroveGyro(GroveBean groveBean, Node parent, NodeResource nodeResource) {
        super(groveBean, parent, nodeResource);
    }

    public double readTemperature() throws WioException {
        return toDouble(readProperty("temperature", "temp"));
    }


    public GyroReading readGyro() throws WioException {
        Map<String, String> map = readPropertyAsMap("gyro");
        GyroReading result = new GyroReading();
        result.setX(toDouble(map.get("gx")));
        result.setY(toDouble(map.get("gy")));
        result.setZ(toDouble(map.get("gz")));
        return result;
    }

    public boolean calibrate() throws WioException {
        return writeProperty("zerocalibrate");
    }

    @Data
    public static class GyroReading {
        private double x, y, z;
    }
}
