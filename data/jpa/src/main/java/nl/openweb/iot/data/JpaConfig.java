package nl.openweb.iot.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import nl.openweb.iot.wio.repository.NodeRepository;

@Configuration
@EntityScan("nl.openweb.iot.data")
@EnableJpaRepositories("nl.openweb.iot.data")
public class JpaConfig {

    @Bean
    @Autowired
    public NodeRepository<String> nodeRepository(JpaNodeRepository jpaNodeRepository) {
        return new RelationalNodeRepository(jpaNodeRepository);
    }
}
