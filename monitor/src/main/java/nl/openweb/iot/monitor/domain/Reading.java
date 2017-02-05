package nl.openweb.iot.monitor.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(indexName = "wio", type = "readings")
public class Reading {

    @Id
    private String id;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String nodeName;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String[] labels;
    @Field(type = FieldType.Date, format = DateFormat.date_time)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ")
    private Date date;
    private Double humidity;
    private Double temperature;
    private Double dust;
    private Integer airQuality;
    private Double luminance;
    private Boolean magneticSwitch;
    private Integer moisture;
    private Integer loudness;
    private Boolean relay;
    private Integer servoAngle;
    private Double ultraRanger;
    private Double co2;



    public Reading(String nodeName) {
        this.nodeName = nodeName;
        this.date = new Date();
    }

}