package nl.openweb.iot.monitor;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import nl.openweb.iot.monitor.repository.ReadingRepository;
import nl.openweb.iot.monitor.strategy.TrivialStrategy;

@ComponentScan("nl.openweb.iot.monitor")
@EnableElasticsearchRepositories("nl.openweb.iot.monitor.repository")
public class MonitorConfig {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Monitor monitor(MonitorSettings settings, ReadingRepository repository, ReadingStrategy strategy) {
        return new Monitor(settings.getPeriodInMin(), repository, strategy);
    }

    @Bean
    @ConditionalOnMissingBean
    public ReadingStrategy readingStrategy() {
        return new TrivialStrategy();
    }
}
