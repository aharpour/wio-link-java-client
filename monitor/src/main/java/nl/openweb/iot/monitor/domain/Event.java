package nl.openweb.iot.monitor.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;
import lombok.NoArgsConstructor;
import nl.openweb.iot.wio.domain.Node;

@Data
@NoArgsConstructor
@Document(indexName = "wio", type = "event")
public class Event {

    @Id
    private String id;
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String nodeName;
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String nodeSn;
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String[] eventKeys;
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String[] eventValues;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ")
    private Date date;

    public Event(Map<String, String> event, Node node) {
        this.nodeName = node.getName();
        this.nodeSn = node.getNodeSn();
        List<String> keys = new ArrayList<>();
        List<String> values = new ArrayList<>();
        for (Map.Entry<String, String> entry : event.entrySet()) {
            keys.add(entry.getKey());
            values.add(entry.getValue());
        }
        this.eventKeys = keys.toArray(new String[keys.size()]);
        this.eventValues = values.toArray(new String[values.size()]);
        this.date = new Date();
    }
}