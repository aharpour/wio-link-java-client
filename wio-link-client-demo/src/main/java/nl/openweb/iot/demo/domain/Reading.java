package nl.openweb.iot.demo.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(indexName = "wio", type = "readings")
public class Reading {

    @Id
    private String id;
    private Double humidity;
    private Double temperature;
    @Field(type = FieldType.Date, format = DateFormat.basic_date_time)
    private Date date;

    public Reading(Double humidity, Double temperature, Date date) {
        this.humidity = humidity;
        this.temperature = temperature;
        this.date = date;
    }
}
