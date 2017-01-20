package nl.openweb.iot;

import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.stereotype.Component;

import nl.openweb.iot.wio.NodeService;
import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.domain.Node;
import nl.openweb.iot.wio.domain.grove.GroveMoisture;

@SpringBootApplication
@EnableFeignClients
public class WioLinkClientDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WioLinkClientDemoApplication.class, args);
    }
}

@Component
class TestRunner implements CommandLineRunner {

    private NodeService nodeService;

    public TestRunner(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @Override
    public void run(String... args) throws Exception {
        Node ebi01 = nodeService.findNodeByName("Ebi01");
        Optional<GroveMoisture> groveMoisture = ebi01.getGroveByType(GroveMoisture.class);
        groveMoisture.ifPresent(m -> {
            try {
                System.out.println(m.readMoisture());
            } catch (WioException e) {
                e.printStackTrace();
            }
        });
    }
}

