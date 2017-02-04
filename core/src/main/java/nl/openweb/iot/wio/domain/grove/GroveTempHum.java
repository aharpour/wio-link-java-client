package nl.openweb.iot.wio.domain.grove;

import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.db.GroveBean;
import nl.openweb.iot.wio.domain.Grove;
import nl.openweb.iot.wio.domain.Node;
import nl.openweb.iot.wio.domain.Type;
import nl.openweb.iot.wio.rest.NodeResource;

@Type
public class GroveTempHum extends Grove {

    public GroveTempHum(GroveBean groveBean, Node parent, NodeResource nodeResource) {
        super(groveBean, parent, nodeResource);
    }

    public Double readHumidity() throws WioException {
        return toDouble(readProperty("humidity"));
    }

    public Double readTemperature() throws WioException {
        return toDouble(readProperty("temperature", "celsius_degree"));
    }

    public Double readTemperatureInFahrenheit() throws WioException {
        return toDouble(readProperty("temperature_f", "fahrenheit_degree"));
    }
}
