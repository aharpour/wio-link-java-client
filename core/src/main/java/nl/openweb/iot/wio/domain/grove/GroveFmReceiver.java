package nl.openweb.iot.wio.domain.grove;

import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.db.GroveBean;
import nl.openweb.iot.wio.domain.Grove;
import nl.openweb.iot.wio.domain.Node;
import nl.openweb.iot.wio.domain.Type;
import nl.openweb.iot.wio.rest.NodeResource;

@Type("GroveI2cFmReceiver")
public class GroveFmReceiver extends Grove {

    public GroveFmReceiver(GroveBean groveBean, Node parent, NodeResource nodeResource) {
        super(groveBean, parent, nodeResource);
    }

    public int readFrequency() throws WioException {
        return toInteger(readProperty("frequency"));
    }
    public int readVolume() throws WioException {
        return toInteger(readProperty("volume", "level"));
    }
    public int readSignalLevel() throws WioException {
        return toInteger(readProperty("signal_level", "rssi"));
    }

    public boolean isMute() throws WioException {
        return "1".equals(readProperty("mute_status", "muted"));
    }

    public boolean writeMute(boolean mute) throws WioException {
        return writeProperty("mute", mute ? "1" : "0");
    }

    public boolean writeVolume(int level) throws WioException {
        return writeProperty("volume", Integer.toString(level));
    }

    public boolean writeFrequency(int frequency) throws WioException {
        return writeProperty("frequency", Integer.toString(frequency));
    }
}
