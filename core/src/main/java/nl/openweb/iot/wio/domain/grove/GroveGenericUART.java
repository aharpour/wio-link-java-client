package nl.openweb.iot.wio.domain.grove;

import java.nio.charset.Charset;
import java.util.Base64;

import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.db.GroveBean;
import nl.openweb.iot.wio.domain.Grove;
import nl.openweb.iot.wio.domain.Node;
import nl.openweb.iot.wio.domain.Type;
import nl.openweb.iot.wio.rest.NodeResource;

@Type("GroveUART")
public class GroveGenericUART extends Grove {

    public GroveGenericUART(GroveBean groveBean, Node parent, NodeResource nodeResource) {
        super(groveBean, parent, nodeResource);
    }

    public boolean writeBaudRate(int rate) throws WioException {
        return writeProperty("baudrate", Integer.toString(rate));
    }

    public boolean writeString(String value) throws WioException {
        String propertyValue = new String(Base64.getEncoder().encode(value.getBytes()), Charset.forName("UTF-8"));
        return writeProperty("base64_string", propertyValue);
    }


}
