package nl.openweb.iot;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.stereotype.Component;

import nl.openweb.iot.wio.rest.NodeResource;
import nl.openweb.iot.wio.rest.NodesResource;
import nl.openweb.iot.wio.rest.SeeedSsoResource;

@SpringBootApplication
@EnableFeignClients
public class WioLinkClientDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WioLinkClientDemoApplication.class, args);
    }
}

@Component
class TestRunner implements CommandLineRunner {

    private SeeedSsoResource userResource;
    private NodesResource nodesResource;
    private NodeResource nodeResource;

    public TestRunner(SeeedSsoResource userResource, NodesResource nodesResource, NodeResource nodeResource) {
        this.userResource = userResource;
        this.nodesResource = nodesResource;
        this.nodeResource = nodeResource;
    }

    @Override
    public void run(String... args) throws Exception {
        SeeedSsoResource.LoginResponse user = userResource.login(args[0], args[1]);
        NodesResource.ListResponse list = nodesResource.list(user.getToken());

        List<NodesResource.ListResponse.Node> nodes = list.getNodes();
        String nodeToken = nodes.get(0).getNodeKey();
        NodeResource.NodeInfo nodeInfo = nodeResource.getNodeInfo(nodeToken);
        List<String> calls = nodeInfo.getCalls();
        for (String call : calls) {
            System.out.println(call);
        }



    }
}

