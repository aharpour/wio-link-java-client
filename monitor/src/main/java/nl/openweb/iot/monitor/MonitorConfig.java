package nl.openweb.iot.monitor;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import nl.openweb.iot.monitor.repository.ReadingRepository;
import nl.openweb.iot.monitor.strategy.TrivialStrategy;
import nl.openweb.iot.wio.scheduling.SchedulingService;

@ComponentScan("nl.openweb.iot.monitor")
@EnableElasticsearchRepositories("nl.openweb.iot.monitor.repository")
public class MonitorConfig {

    @Bean
    @ConditionalOnProperty(prefix = "wio.monitor", name = "automatic", matchIfMissing = true)
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Monitor monitor(MonitorSettings settings, ReadingRepository repository, ReadingStrategy strategy) {
        return new Monitor(settings.getPeriodInMin(), repository, strategy);
    }

    @Bean
    @ConditionalOnMissingBean
    public ReadingStrategy readingStrategy() {
        return new TrivialStrategy();
    }

    @Bean
    @ConditionalOnProperty(prefix = "wio.monitor", name = "automatic", matchIfMissing = true)
    public MonitorRunner monitorRunner(SchedulingService schedulingService, MonitorSettings settings,
                                       ReadingRepository readingRepository, ApplicationContext applicationContext) {
        return new MonitorRunner(schedulingService, settings, readingRepository, applicationContext);
    }
}
