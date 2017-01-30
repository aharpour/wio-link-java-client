package nl.openweb.iot.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import nl.openweb.iot.wio.repository.NodeRepository;

@Configuration
@EntityScan("nl.openweb.iot.data")
@EnableElasticsearchRepositories("nl.openweb.iot.data")
public class ElasticConfig {

    @Bean
    @Autowired
    public NodeRepository<String> nodeRepository(ElasticNodeRepository elasticNodeRepository) {
        return new ENodeRepository(elasticNodeRepository);
    }
}
