package nl.openweb.iot.monitor.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import nl.openweb.iot.monitor.domain.Event;

public interface EventRepository extends ElasticsearchRepository<Event, String> {
}
