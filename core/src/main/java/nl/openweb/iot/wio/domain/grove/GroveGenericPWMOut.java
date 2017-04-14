package nl.openweb.iot.wio.domain.grove;

import java.util.Map;

import org.springframework.data.util.Pair;

import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.db.GroveBean;
import nl.openweb.iot.wio.domain.Grove;
import nl.openweb.iot.wio.domain.Node;
import nl.openweb.iot.wio.domain.Type;
import nl.openweb.iot.wio.rest.NodeResource;

@Type("GenericPWMOut")
public class GroveGenericPWMOut extends Grove {

    public GroveGenericPWMOut(GroveBean groveBean, Node parent, NodeResource nodeResource) {
        super(groveBean, parent, nodeResource);
    }

    public Pair<Double, Integer> readPWM() throws WioException {
        Map<String, String> pwm = this.nodeResource.readProperty(parent.getNodeKey(), name, "pwm");
        return Pair.of(toDouble(pwm.get("duty_percent")), toInteger(pwm.get("freq")));
    }

    public boolean writePWM(double dutyPercent, int freq) throws WioException {
        return writeProperty("pwm_with_freq", Double.toString(dutyPercent), Integer.toString(freq));
    }

    public boolean writePWM(double dutyPercent) throws WioException {
        return writeProperty("pwm", Double.toString(dutyPercent));
    }


}
