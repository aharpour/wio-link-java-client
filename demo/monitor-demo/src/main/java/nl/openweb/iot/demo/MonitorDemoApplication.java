package nl.openweb.iot.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import nl.openweb.iot.monitor.MonitorRunner;
import nl.openweb.iot.monitor.MonitorSettings;
import nl.openweb.iot.monitor.repository.ReadingRepository;
import nl.openweb.iot.monitor.strategy.AveragingStrategy;
import nl.openweb.iot.wio.scheduling.SchedulingService;

@SpringBootApplication
public class MonitorDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitorDemoApplication.class, args);
    }

    @Bean
    public MonitorRunner monitorRunner(SchedulingService schedulingService, MonitorSettings settings,
                                       ReadingRepository readingRepository, ApplicationContext applicationContext) {
        return new MonitorRunner(schedulingService, settings, readingRepository, applicationContext);
    }

    @Bean
    public AveragingStrategy averagingStrategy() {
        return new AveragingStrategy();
    }

}
