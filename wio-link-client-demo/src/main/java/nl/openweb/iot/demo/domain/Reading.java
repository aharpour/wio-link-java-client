package nl.openweb.iot.demo.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

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
    private Date date;

    public Reading(Double humidity, Double temperature, Date date) {
        this.humidity = humidity;
        this.temperature = temperature;
        this.date = date;
    }
}
