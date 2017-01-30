package nl.openweb.iot.data;


import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticNodeRepository extends ElasticsearchRepository<ElasticNodeBean, String> {

    ElasticNodeBean findOneByName(String name);

}
