package nl.openweb.iot.wio;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties("wio")
@Setter
@Getter
public class WioSettings {

    private String baseUrl = "https://us.wio.seeed.io";
    private String username;
    private String password;
    private String userToken;
    private boolean trySeedSso = true;

}
