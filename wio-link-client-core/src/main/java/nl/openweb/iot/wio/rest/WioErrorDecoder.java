package nl.openweb.iot.wio.rest;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.Data;


public class WioErrorDecoder extends ErrorDecoder.Default implements ErrorDecoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(WioErrorDecoder.class);

    private ObjectMapper objectMapper;

    public Exception decode(String methodKey, Response response) {
        Exception result;
        if (response.status() == 400 || response.status() == 404 || response.status() == 408) {
            ErrorResponse errorResponse = getErrorResponse(response);
            if (errorResponse != null) {
                result = new WioRestException(errorResponse.getError(), response.status());
            } else {
                result = new WioRestException("Error message is not available", response.status());
            }
        } else {
            result = super.decode(methodKey, response);
        }
        return result;
    }

    private ErrorResponse getErrorResponse(Response response) {
        ErrorResponse result = null;
        try {
            result = objectMapper.readValue(response.body().asInputStream(), ErrorResponse.class);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return result;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Data
    private static class ErrorResponse {
        private String error;
    }
}
