package nl.openweb.iot.demo;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import nl.openweb.iot.demo.domain.Reading;
import nl.openweb.iot.demo.repository.ReadingRepository;
import nl.openweb.iot.wio.domain.grove.GroveTempHumPro;
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
            ReadingRepository repository = context.getBean(ReadingRepository.class);
            GroveTempHumPro temp = node.getGroveByType(GroveTempHumPro.class).get();
            repository.save(new Reading(temp.readHumidity(), temp.readTemperature(), new Date()));
            return SchedulingUtils.minutesLater(5);
        }).setEventHandler((e, n, c) -> System.out.println(e)).setForceSleep(false).build();
    }
}
