package nl.openweb.iot.wio.domain;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import lombok.Getter;
import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.db.GroveBean;
import nl.openweb.iot.wio.rest.NodeResource;

public abstract class Grove {

    @Getter
    protected final String name, type;

    @Getter
    protected final boolean passive;
    @Getter
    protected Node parent;

    protected final NodeResource nodeResource;

    public Grove(GroveBean groveBean, Node parent, NodeResource nodeResource) {
        this.name = groveBean.getName();
        this.type = groveBean.getType();
        this.passive = groveBean.isPassive();
        this.parent = parent;
        this.nodeResource = nodeResource;
    }

    public int getWarmUpTime() {
        return 0;
    }

    protected boolean writeProperty(String propertyName, String value) throws WioException {
        NodeResource.WriteResponse response = nodeResource.writeProperty(parent.getNodeKey(), name, propertyName, value);
        return "ok".equals(response.getResult());
    }


    protected boolean writeProperty(String propertyName, String value1, String value2) throws WioException {
        NodeResource.WriteResponse response = nodeResource.writeProperty(parent.getNodeKey(), name, propertyName, value1, value2);
        return "ok".equals(response.getResult());
    }

    protected String readProperty(String propertyName) throws WioException {
        return this.readProperty(propertyName, propertyName);
    }

    protected String readProperty(String propertyName, String jsonName) throws WioException {
        return this.nodeResource.readProperty(parent.getNodeKey(), name, propertyName).get(jsonName);
    }

    protected static Double toDouble(String value) {
        Double result = null;
        if (StringUtils.isNotBlank(value) && NumberUtils.isNumber(value)) {
            result = Double.parseDouble(value);
        }
        return result;
    }

    protected static Integer toInteger(String value) {
        Integer result = null;
        if (StringUtils.isNotBlank(value) && NumberUtils.isNumber(value)) {
            result = Integer.parseInt(value);
        }
        return result;
    }
}
