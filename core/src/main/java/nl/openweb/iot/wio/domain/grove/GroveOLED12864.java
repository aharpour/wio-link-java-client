package nl.openweb.iot.wio.domain.grove;

import nl.openweb.iot.wio.db.GroveBean;
import nl.openweb.iot.wio.domain.Node;
import nl.openweb.iot.wio.domain.Type;
import nl.openweb.iot.wio.rest.NodeResource;

@Type
public class GroveOLED12864 extends GroveOLED {

    public GroveOLED12864(GroveBean groveBean, Node parent, NodeResource nodeResource) {
        super(groveBean, parent, nodeResource);
    }
}
