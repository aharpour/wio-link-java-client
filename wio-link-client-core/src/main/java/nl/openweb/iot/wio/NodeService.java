package nl.openweb.iot.wio;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.openweb.iot.wio.db.NodeBean;
import nl.openweb.iot.wio.domain.Node;
import nl.openweb.iot.wio.repository.NodeRepository;
import nl.openweb.iot.wio.rest.NodeResource;
import nl.openweb.iot.wio.rest.NodesResource;
import nl.openweb.iot.wio.rest.SeeedSsoResource;
import nl.openweb.iot.wio.rest.UserResource;

@Service
public class NodeService {


    private NodeRepository nodeRepository;
    private NodeResource nodeResource;
    private NodesResource nodesResource;
    private UserResource userResource;
    private SeeedSsoResource ssoResource;
    private Map<String, Node> registry = new ConcurrentHashMap<>();


    @PostConstruct
    public void initialize() {
        if (nodeRepository.count() == 0) {

        }
    }

    public NodeBean findNodeByName(String nodeName) {
        NodeBean node = this.nodeRepository.findOneByName(nodeName);
        if (node != null) {

        }
        return node;
    }

    @Autowired
    public void setNodeRepository(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    @Autowired
    public void setNodeResource(NodeResource nodeResource) {
        this.nodeResource = nodeResource;
    }

    @Autowired
    public void setNodesResource(NodesResource nodesResource) {
        this.nodesResource = nodesResource;
    }

    @Autowired
    public void setSsoResource(SeeedSsoResource ssoResource) {
        this.ssoResource = ssoResource;
    }
}
