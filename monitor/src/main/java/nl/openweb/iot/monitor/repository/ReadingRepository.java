package nl.openweb.iot.monitor.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import nl.openweb.iot.monitor.domain.Reading;


public interface ReadingRepository extends ElasticsearchRepository<Reading, String> {
}
