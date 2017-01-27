package nl.openweb.iot.monitor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component
@ConfigurationProperties("wio.monitor")
public class MonitorSettings {
    private int periodInMin = 10;
    private boolean keepAwake = false;
    private boolean forceSleep = false;
    private List<String> nodeNames = new ArrayList<String>();
    private int numberOfReadingToAverage = 3;
}
