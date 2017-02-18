package nl.openweb.iot.demo;

import java.net.UnknownHostException;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import nl.openweb.iot.dashboard.DashboardAppConfig;

@SpringBootApplication
public class DashboardDemoApplication {

    public static void main(String[] args) throws UnknownHostException {
        DashboardAppConfig.run(args);
    }

}
