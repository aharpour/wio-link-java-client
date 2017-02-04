package nl.openweb.iot.wio.domain.grove;

import java.nio.charset.Charset;
import java.util.Base64;

import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.db.GroveBean;
import nl.openweb.iot.wio.domain.Grove;
import nl.openweb.iot.wio.domain.Node;
import nl.openweb.iot.wio.rest.NodeResource;

public abstract class GroveOLED extends Grove {

    public GroveOLED(GroveBean groveBean, Node parent, NodeResource nodeResource) {
        super(groveBean, parent, nodeResource);
    }

    public boolean clear() throws WioException {
        return writeProperty("clear");
    }

    public boolean stopScroll() throws WioException {
        return writeProperty("stop_scroll");
    }

    public boolean printFloatNumber(int row, int col, double number, int decimals) throws WioException {
        return writeProperty("float", Integer.toString(row), Integer.toString(col), Double.toString(number),
                Integer.toString(decimals));
    }

    public boolean printInteger(int row, int col, int number) throws WioException {
        return writeProperty("integer", Integer.toString(row), Integer.toString(col), Double.toString(number));
    }

    public boolean printString(int row, int col, String string) throws WioException {
        String base64 = new String(Base64.getEncoder().encode(string.getBytes()), Charset.forName("UTF-8"));
        return writeProperty("base64_string", Integer.toString(row), Integer.toString(col), base64);
    }

    public boolean printSingleLineString(int row, int col, String string) throws WioException {
        return writeProperty("string", Integer.toString(row), Integer.toString(col), string);
    }

    public boolean setBrightness(int brightness) throws WioException {
        return writeProperty("brightness", Integer.toString(brightness));
    }

    public boolean inverseDisplay(boolean inverseDisplay) throws WioException {
        return writeProperty("inverse_display", inverseDisplay ? "1" : "0");
    }

    public boolean scrollRight(int startRow, int endRow, int speed) throws WioException {
        return writeProperty("scroll_right", Integer.toString(startRow), Integer.toString(endRow), Integer.toString(speed));
    }

    public boolean scrollLeft(int startRow, int endRow, int speed) throws WioException {
        return writeProperty("scroll_left", Integer.toString(startRow), Integer.toString(endRow), Integer.toString(speed));
    }
}
