package nl.openweb.iot.wio;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import nl.openweb.iot.wio.repository.InMemoryNodeRepository;
import nl.openweb.iot.wio.repository.NodeRepository;

@EnableFeignClients
@ComponentScan("nl.openweb.iot.wio")
@Configuration
public class WioConfig {

    @Bean(destroyMethod = "shutdown")
    public TaskScheduler taskScheduler(WioSettings wioSettings) {
        final ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(wioSettings.getTaskSchedulerPoolSize());
        taskScheduler.setWaitForTasksToCompleteOnShutdown(false);
        taskScheduler.setAwaitTerminationSeconds(10);
        taskScheduler.initialize();
        return taskScheduler;
    }

    @Bean
    @ConditionalOnMissingBean(NodeRepository.class)
    public NodeRepository<String> nodeRepository() {
        return new InMemoryNodeRepository();
    }
}
