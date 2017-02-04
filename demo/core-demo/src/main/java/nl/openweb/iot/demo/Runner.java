package nl.openweb.iot.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import nl.openweb.iot.wio.domain.grove.GroveTempHumPro;
import nl.openweb.iot.wio.scheduling.ScheduledTask;
import nl.openweb.iot.wio.scheduling.SchedulingService;
import nl.openweb.iot.wio.scheduling.SchedulingUtils;

@Component
public class Runner implements CommandLineRunner {

    private SchedulingService schedulingService;

    @Autowired
    public Runner(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }

    @Override
    public void run(String... args) throws Exception {
        schedulingService.build("Ebi01", (node, context) -> {
            ScheduledTask.TaskExecutionResult taskExecutionResult = SchedulingUtils.minutesLater(5);
            GroveTempHumPro temp = node.getGroveByType(GroveTempHumPro.class).get();
            System.out.println("Temperature: " + temp.readTemperature());
            System.out.println("Humidity: " + temp.readHumidity());
            return taskExecutionResult;
        }).setEventHandler((e, n, c) -> System.out.println(e)).setForceSleep(false).build();
    }
}
