package nl.openweb.iot.wio.domain.grove;

import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.db.GroveBean;
import nl.openweb.iot.wio.domain.Grove;
import nl.openweb.iot.wio.domain.Node;
import nl.openweb.iot.wio.domain.Type;
import nl.openweb.iot.wio.rest.NodeResource;

@Type
public class GroveMultiChannelGas extends Grove {

    public GroveMultiChannelGas(GroveBean groveBean, Node parent, NodeResource nodeResource) {
        super(groveBean, parent, nodeResource);
    }

    public double readNH3() throws WioException {
        return toDouble(readProperty("NH3", "concentration_ppm"));
    }

    public double readCH4() throws WioException {
        return toDouble(readProperty("CH4", "concentration_ppm"));
    }

    public double readC4H10() throws WioException {
        return toDouble(readProperty("C4H10", "concentration_ppm"));
    }

    public double readNO2() throws WioException {
        return toDouble(readProperty("NO2", "concentration_ppm"));
    }

    public double readC2H5OH() throws WioException {
        return toDouble(readProperty("C2H5OH", "concentration_ppm"));
    }

    public double readCO() throws WioException {
        return toDouble(readProperty("CO", "concentration_ppm"));
    }

    public double readC3H8() throws WioException {
        return toDouble(readProperty("C3H8", "concentration_ppm"));
    }

    public double readH2() throws WioException {
        return toDouble(readProperty("H2", "concentration_ppm"));
    }
}
