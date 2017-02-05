package nl.openweb.iot.wio.domain.grove;

import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.db.GroveBean;
import nl.openweb.iot.wio.domain.Grove;
import nl.openweb.iot.wio.domain.Node;
import nl.openweb.iot.wio.rest.NodeResource;

public class GenericGrove extends Grove {

    public GenericGrove(GroveBean groveBean, Node parent, NodeResource nodeResource) {
        super(groveBean, parent, nodeResource);
    }

    @Override
    public boolean writeProperty(String propertyName, String value) throws WioException {
        return super.writeProperty(propertyName, value);
    }

    @Override
    public boolean writeProperty(String propertyName) throws WioException {
        return super.writeProperty(propertyName);
    }

    @Override
    public boolean writeProperty(String propertyName, String value1, String value2) throws WioException {
        return super.writeProperty(propertyName, value1, value2);
    }

    @Override
    public boolean writeProperty(String propertyName, String value1, String value2, String value3) throws WioException {
        return super.writeProperty(propertyName, value1, value2, value3);
    }

    @Override
    public boolean writeProperty(String propertyName, String value1, String value2, String value3, String value4) throws WioException {
        return super.writeProperty(propertyName, value1, value2, value3, value4);
    }

    @Override
    public String readProperty(String propertyName) throws WioException {
        return super.readProperty(propertyName);
    }

    @Override
    public String readProperty(String propertyName, String jsonName) throws WioException {
        return super.readProperty(propertyName, jsonName);
    }

}
