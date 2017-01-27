package nl.openweb.iot.monitor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;

import nl.openweb.iot.monitor.repository.ReadingRepository;
import nl.openweb.iot.wio.scheduling.SchedulingService;

public class MonitorRunner implements CommandLineRunner {

    private final SchedulingService schedulingService;
    private final MonitorSettings settings;
    private final ReadingRepository readingRepository;
    private final ApplicationContext applicationContext;

    @Autowired
    public MonitorRunner(SchedulingService schedulingService, MonitorSettings settings,
                         ReadingRepository readingRepository, ApplicationContext applicationContext) {
        this.schedulingService = schedulingService;
        this.settings = settings;
        this.readingRepository = readingRepository;
        this.applicationContext = applicationContext;
    }

    @Override
    public void run(String... args) throws Exception {
        List<String> nodeNames = settings.getNodeNames();
        for (String nodeName : nodeNames) {
            schedulingService.build(nodeName, applicationContext.getBean(Monitor.class))
                    .setKeepAwake(settings.isKeepAwake())
                    .setForceSleep(settings.isForceSleep())
                    .build();
        }

    }
}
