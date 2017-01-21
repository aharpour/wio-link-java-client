package nl.openweb.iot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.stereotype.Component;

import nl.openweb.iot.wio.NodeService;
import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.domain.Node;
import nl.openweb.iot.wio.domain.grove.GroveMagneticSwitch;
import nl.openweb.iot.wio.domain.grove.GroveMoisture;
import nl.openweb.iot.wio.domain.grove.GroveTempHumPro;

@SpringBootApplication
@EnableFeignClients
public class WioLinkClientDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WioLinkClientDemoApplication.class, args);
    }
}

@Component
class Runner implements CommandLineRunner {

    private NodeService nodeService;

    public Runner(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @Override
    public void run(String... args) throws Exception {
        Node ebi01 = nodeService.findNodeByName("Ebi01");
        System.out.println("GroveMoisture");
        ebi01.getGroveByType(GroveMoisture.class).ifPresent(m -> {
            try {
                System.out.println("Moisture: " + m.readMoisture());
                System.out.println("isPassive: " + m.isPassive());
            } catch (WioException e) {
                e.printStackTrace();
            }
        });
        System.out.println("GroveTempHumPro");
        ebi01.getGroveByType(GroveTempHumPro.class).ifPresent(m -> {
            try {
                System.out.println("Humidity: " + m.readHumidity());
                System.out.println("Temperature: " + m.readTemperature());
                System.out.println("TemperatureInFahrenheit: " + m.readTemperatureInFahrenheit());
                System.out.println("isPassive: " + m.isPassive());
            } catch (WioException e) {
                e.printStackTrace();
            }
        });
        System.out.println("GroveMagneticSwitch");
        ebi01.getGroveByType(GroveMagneticSwitch.class).ifPresent(m -> {
            try {
                System.out.println("Approach: " + m.readApproach());
                System.out.println("isPassive: " + m.isPassive());
            } catch (WioException e) {
                e.printStackTrace();
            }
        });
        System.out.println(ebi01.getGroves());
    }
}

