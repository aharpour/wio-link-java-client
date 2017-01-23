package nl.openweb.iot.demo.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import nl.openweb.iot.demo.domain.Reading;

public interface ReadingRepository extends ElasticsearchRepository<Reading, String> {
}
