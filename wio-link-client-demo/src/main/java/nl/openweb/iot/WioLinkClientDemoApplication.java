package nl.openweb.iot;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import nl.openweb.iot.wio.domain.grove.GroveMagneticSwitch;
import nl.openweb.iot.wio.domain.grove.GroveMoisture;
import nl.openweb.iot.wio.domain.grove.GroveTempHumPro;
import nl.openweb.iot.wio.scheduling.SchedulingService;


import static nl.openweb.iot.wio.scheduling.SchedulingUtils.minutesLater;

@SpringBootApplication
public class WioLinkClientDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WioLinkClientDemoApplication.class, args);
    }
}

@Component
class Runner implements CommandLineRunner {

    private SchedulingService schedulingService;

    @Autowired
    public Runner(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }

    @Override
    public void run(String... args) throws Exception {
        schedulingService.build("Ebi01", (node, context) -> {
            System.out.println("Running: " + new Date());
            Optional<GroveTempHumPro> temp = node.getGroveByType(GroveTempHumPro.class);
            System.out.println(temp.get().readHumidity());
            System.out.println(temp.get().readTemperature());
            GroveMoisture moisture = node.getGroveByType(GroveMoisture.class).get();
            System.out.println(moisture.readMoisture());
            GroveMagneticSwitch magneticSwitch = node.getGroveByType(GroveMagneticSwitch.class).get();
            System.out.println(magneticSwitch.readApproach());
            return minutesLater(30);
        }).setEventHandler((e, n, c) -> System.out.println(e)).setForceSleep(false).build();
    }
}

