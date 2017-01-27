package nl.openweb.iot.monitor;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import nl.openweb.iot.monitor.repository.ReadingRepository;

@EnableElasticsearchRepositories("nl.openweb.iot.monitor.repository")
public class MonitorConfig {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    private Monitor monitor(MonitorSettings settings, ReadingRepository repository, ReadingStrategy strategy) {
        return new Monitor(settings.getPeriodInMin(), repository, strategy);
    }
}
