package nl.openweb.iot.wio.domain;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import lombok.Getter;
import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.db.GroveBean;
import nl.openweb.iot.wio.rest.NodeResource;

import java.util.Map;

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

    protected boolean writeProperty(String propertyName) throws WioException {
        return writeResponseToBoolean(nodeResource.writeProperty(parent.getNodeKey(), name, propertyName));
    }

    protected boolean writeProperty(String propertyName, String value) throws WioException {
        return writeResponseToBoolean(nodeResource.writeProperty(parent.getNodeKey(), name, propertyName, value));
    }

    protected boolean writeProperty(String propertyName, String value1, String value2) throws WioException {
        return writeResponseToBoolean(nodeResource.writeProperty(parent.getNodeKey(), name, propertyName, value1, value2));
    }

    protected boolean writeProperty(String propertyName, String value1, String value2, String value3) throws WioException {
        return writeResponseToBoolean(nodeResource.writeProperty(parent.getNodeKey(), name, propertyName, value1, value2, value3));
    }

    protected boolean writeProperty(String propertyName, String value1, String value2, String value3, String value4) throws WioException {
        return writeResponseToBoolean(nodeResource.writeProperty(parent.getNodeKey(), name, propertyName, value1, value2, value3, value4));
    }

    protected Map<String, String> readPropertyAsMap(String propertyName) throws WioException {
        return this.nodeResource.readProperty(parent.getNodeKey(), name, propertyName);
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

    private boolean writeResponseToBoolean(NodeResource.WriteResponse response) {
        return "ok".equals(response.getResult());
    }
}
