package nl.openweb.iot.wio.domain.grove;

import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.db.GroveBean;
import nl.openweb.iot.wio.domain.Grove;
import nl.openweb.iot.wio.domain.Node;
import nl.openweb.iot.wio.domain.Type;
import nl.openweb.iot.wio.rest.NodeResource;

@Type
public class GroveSpeaker extends Grove {

    public GroveSpeaker(GroveBean groveBean, Node parent, NodeResource nodeResource) {
        super(groveBean, parent, nodeResource);
    }

    public boolean startMakingSound(int freq) throws WioException {
        return writeProperty("sound_start", Integer.toString(freq));
    }

    public boolean stopMakingSound() throws WioException {
        return writeProperty("sound_stop");
    }

    public boolean makeSound(int freq, int durationMs) throws WioException {
        return writeProperty("sound_ms", Integer.toString(freq), Integer.toString(durationMs));
    }


}
