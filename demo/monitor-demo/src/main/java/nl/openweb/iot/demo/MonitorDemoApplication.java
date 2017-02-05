package nl.openweb.iot.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import nl.openweb.iot.monitor.strategy.AveragingStrategy;

@SpringBootApplication
public class MonitorDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitorDemoApplication.class, args);
    }

    @Bean
    public AveragingStrategy averagingStrategy() {
        return new AveragingStrategy();
    }

}
