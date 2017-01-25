package nl.openweb.iot.monitor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.stereotype.Component;

import nl.openweb.iot.monitor.repository.ReadingRepository;
import nl.openweb.iot.wio.scheduling.SchedulingService;

@Component
@EnableElasticsearchRepositories("nl.openweb.iot.monitor.repository")
public class MonitorRunner implements CommandLineRunner {

    private final SchedulingService schedulingService;
    private final MonitorSettings settings;
    private final ReadingRepository readingRepository;

    @Autowired
    public MonitorRunner(SchedulingService schedulingService, MonitorSettings settings, ReadingRepository readingRepository) {
        this.schedulingService = schedulingService;
        this.settings = settings;
        this.readingRepository = readingRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<String> nodeNames = settings.getNodeNames();
        for (String nodeName : nodeNames) {
            schedulingService.build(nodeName, new Monitor(settings.getPeriodInMin(), readingRepository))
                    .setKeepAwake(settings.isKeepAwake())
                    .setForceSleep(settings.isForceSleep())
                    .build();
        }

    }
}
