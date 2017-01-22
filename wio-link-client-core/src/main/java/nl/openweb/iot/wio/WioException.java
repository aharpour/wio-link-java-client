package nl.openweb.iot.wio;

public class WioException extends Exception {

    public WioException(String message) {
        super(message);
    }

    public WioException(String message, Throwable cause) {
        super(message, cause);
    }
}
