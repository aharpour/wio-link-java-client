package nl.openweb.iot.dashboard.service.util;

import java.nio.charset.Charset;

public class ByteArrayUtils {

    private ByteArrayUtils() {
    }

    public static String toString(byte[] code) {
        return new String(code, Charset.forName("UTF-8"));
    }


}
