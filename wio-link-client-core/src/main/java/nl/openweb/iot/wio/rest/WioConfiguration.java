package nl.openweb.iot.wio.rest;

import org.springframework.cloud.netflix.feign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WioConfiguration extends FeignClientsConfiguration {

    @Bean(name = "user-resource")
    public WioErrorDecoder wioErrorDecoder() {
        return new WioErrorDecoder();
    }
}
