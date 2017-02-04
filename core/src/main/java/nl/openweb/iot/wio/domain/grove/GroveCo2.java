package nl.openweb.iot.wio.domain.grove;

import java.util.Map;

import lombok.Data;
import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.db.GroveBean;
import nl.openweb.iot.wio.domain.Grove;
import nl.openweb.iot.wio.domain.Node;
import nl.openweb.iot.wio.domain.Type;
import nl.openweb.iot.wio.rest.NodeResource;

@Type("GroveCo2MhZ16")
public class GroveCo2 extends Grove {

    public GroveCo2(GroveBean groveBean, Node parent, NodeResource nodeResource) {
        super(groveBean, parent, nodeResource);
    }

    public Double readTemperature() throws WioException {
        return toDouble(readProperty("temperature"));
    }

    public Double readConcentration() throws WioException {
        return toDouble(readProperty("concentration"));
    }

    public Concentration readConcentrationAndTemperature() throws WioException {
        return new Concentration(
                this.nodeResource.readProperty(parent.getNodeKey(), name, "concentration_and_temperature")
        );
    }

    @Override
    public int getWarmUpTime() {
        return 90;
    }

    @Data
    public static class Concentration {
        private double concentration, temperature;

        public Concentration(Map<String, String> readings) {
            concentration = toDouble(readings.get("concentration"));
            temperature = toDouble(readings.get("temperature"));
        }
    }

}
