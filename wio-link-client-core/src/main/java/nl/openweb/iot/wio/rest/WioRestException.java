package nl.openweb.iot.wio.rest;

import nl.openweb.iot.wio.WioException;

public class WioRestException extends WioException {

    private final int responseStatus;

    public WioRestException(String message, int responseStatus) {
        super(message + " (Response status code: " + responseStatus + ")");
        this.responseStatus = responseStatus;
    }
}
