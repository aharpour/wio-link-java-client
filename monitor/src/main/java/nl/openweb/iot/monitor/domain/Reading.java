package nl.openweb.iot.monitor.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.domain.Grove;
import nl.openweb.iot.wio.domain.grove.*;

@Data
@NoArgsConstructor
@Document(indexName = "wio", type = "readings")
public class Reading {

    @Id
    private String id;
    @Field(index = FieldIndex.not_analyzed)
    private String nodeName;
    @Field(index = FieldIndex.not_analyzed)
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
    private Boolean relay;
    private Integer servoAngle;
    private Double ultraRanger;


    public Reading(String nodeName) {
        this.nodeName = nodeName;
        this.date = new Date();
    }

    public void addData(Grove grove) throws WioException {
        if (GroveTempHumPro.class.isAssignableFrom(grove.getClass())) {
            addData((GroveTempHumPro) grove);
        } else if (GroveLuminance.class.isAssignableFrom(grove.getClass())) {
            addData((GroveLuminance) grove);
        } else if (GroveDust.class.isAssignableFrom(grove.getClass())) {
            addData((GroveDust) grove);
        } else if (GroveAirQuality.class.isAssignableFrom(grove.getClass())) {
            addData((GroveAirQuality) grove);
        } else if (GroveRelay.class.isAssignableFrom(grove.getClass())) {
            addData((GroveRelay) grove);
        } else if (GroveMoisture.class.isAssignableFrom(grove.getClass())) {
            addData((GroveMoisture) grove);
        } else if (GroveMagneticSwitch.class.isAssignableFrom(grove.getClass())) {
            addData((GroveMagneticSwitch) grove);
        } else if (GroveUltraRanger.class.isAssignableFrom(grove.getClass())) {
            addData((GroveUltraRanger) grove);
        } else if (GroveServo.class.isAssignableFrom(grove.getClass())) {
            addData((GroveServo) grove);
        }
    }

    public void addData(GroveTempHumPro grove) throws WioException {
        this.temperature = grove.readTemperature();
        this.humidity = grove.readHumidity();
    }

    public void addData(GroveLuminance grove) throws WioException {
        this.luminance = grove.readLuminance();
    }

    public void addData(GroveDust grove) throws WioException {
        this.dust = grove.readDust();
    }

    public void addData(GroveAirQuality grove) throws WioException {
        this.airQuality = grove.readQuality();
    }

    public void addData(GroveRelay grove) throws WioException {
        this.relay = 1 == grove.readOnOff();
    }

    public void addData(GroveMoisture grove) throws WioException {
        this.moisture = grove.readMoisture();
    }

    public void addData(GroveMagneticSwitch grove) throws WioException {
        this.magneticSwitch = 1 == grove.readApproach();
    }

    public void addData(GroveUltraRanger grove) throws WioException {
        this.ultraRanger = grove.readRangeInCm();
    }

    public void addData(GroveServo grove) throws WioException {
        this.servoAngle = grove.readAngle();
    }
}