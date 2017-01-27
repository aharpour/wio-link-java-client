package nl.openweb.iot.wio;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.openweb.iot.wio.db.GroveBean;
import nl.openweb.iot.wio.db.NodeBean;
import nl.openweb.iot.wio.domain.GroveFactory;
import nl.openweb.iot.wio.domain.Node;
import nl.openweb.iot.wio.domain.NodeImpl;
import nl.openweb.iot.wio.repository.NodeRepository;
import nl.openweb.iot.wio.rest.NodeResource;
import nl.openweb.iot.wio.rest.NodesResource;
import nl.openweb.iot.wio.rest.SeeedSsoResource;
import nl.openweb.iot.wio.rest.UserResource;

@Service
public class NodeService {

    private static final Pattern CALL_PATTERN = Pattern.compile("^([a-zA-Z]*) /v1/node/(([a-zA-Z]*)(D|A|U|I2C)[0-9])/[^\\s]* -> .*$");
    private static final Pattern EVENT_PATTERN = Pattern.compile("^Event (([a-zA-Z]*)(D|A|U|I2C)[0-9]) [^\\s]*$");

    private static final Logger LOG = LoggerFactory.getLogger(NodeService.class);

    private WioSettings wioSettings;
    private NodeRepository nodeRepository;
    private NodeResource nodeResource;
    private NodesResource nodesResource;
    private UserResource userResource;
    private SeeedSsoResource ssoResource;
    private WebSocketService webSocketService;
    private GroveFactory groveFactory;
    private Map<String, Node> registry = new ConcurrentHashMap<>();


    @PostConstruct
    public void initialize() throws WioException {
        if (nodeRepository.count() == 0) {
            String userToken = getUserToken();
            NodesResource.ListResponse list = nodesResource.list(userToken);
            for (NodesResource.ListResponse.Node node : list.getNodes()) {
                NodeBean nodeBean = new NodeBean(node);
                if (node.isOnline()) {
                    try {
                        initializeNodeBean(nodeBean);
                    } catch (WioException e) {
                        LOG.warn("Failed to initialize node " + nodeBean.getName(), e);
                    }
                }
                nodeRepository.save(nodeBean);
            }
        }
    }

    public Node findNodeBySnId(String nodeSn) throws WioException {
        NodeBean nodeBean = this.nodeRepository.findOneNodeSn(nodeSn);
        return createNodeFromNodeBean(nodeBean);
    }

    public Node findNodeByName(String nodeName) throws WioException {
        NodeBean nodeBean = this.nodeRepository.findOneByName(nodeName);
        return createNodeFromNodeBean(nodeBean);
    }

    private Node createNodeFromNodeBean(NodeBean nodeBean) throws WioException {
        Node result = null;
        initializeAndUpdateNodeBean(nodeBean);
        if (nodeBean != null) {
            result = new NodeImpl(nodeBean, groveFactory, nodeResource, webSocketService);
        }
        return result;
    }


    private void initializeAndUpdateNodeBean(NodeBean node) throws WioException {
        if (node != null && !node.getInitialized()) {
            initializeNodeBean(node);
            this.nodeRepository.save(node);
        }
    }

    private void initializeNodeBean(NodeBean nodeBean) throws WioException {
        List<GroveBean> groves = getGroveBeans(nodeBean);
        nodeBean.setGroves(groves);
        nodeBean.setInitialized(true);
    }

    private String getUserToken() throws WioException {
        String userToken = wioSettings.getUserToken();
        if (StringUtils.isBlank(userToken)) {
            if (StringUtils.isNotBlank(wioSettings.getUsername()) && StringUtils.isNotBlank(wioSettings.getPassword())) {
                try {
                    UserResource.LoginResponse response = userResource.login(wioSettings.getUsername(), wioSettings.getPassword());
                    userToken = response.getToken();
                } catch (WioException e) {
                    if (wioSettings.isTrySeedSso()) {
                        SeeedSsoResource.LoginResponse login = ssoResource.login(wioSettings.getUsername(), wioSettings.getPassword());
                        userToken = login.getToken();
                    }
                }
            } else {
                throw new WioException("Either user token or username and password needs to be set.");
            }
        }
        return userToken;
    }

    private List<GroveBean> getGroveBeans(NodeBean node) throws WioException {
        List<GroveBean> candidates = new ArrayList<>();
        NodeResource.NodeInfo nodeInfo = nodeResource.getNodeInfo(node.getNodeKey());
        for (String call : nodeInfo.getCalls()) {
            Matcher matcher = CALL_PATTERN.matcher(call);
            if (matcher.matches()) {
                candidates.add(new GroveBean(matcher.group(2), matcher.group(3), true));
            } else {
                matcher = EVENT_PATTERN.matcher(call);
                if (matcher.matches()) {
                    candidates.add(new GroveBean(matcher.group(1), matcher.group(2), false));
                } else {
                    LOG.error("Unrecognized call pattern: " + call);
                }
            }
        }
        return mergeDuplicates(candidates);
    }

    private List<GroveBean> mergeDuplicates(List<GroveBean> candidates) {
        Map<String, GroveBean> map = new LinkedHashMap<>();
        for (GroveBean candidate : candidates) {
            if (map.containsKey(candidate.getName())) {
                map.get(candidate.getName()).update(candidate);
            } else {
                map.put(candidate.getName(), candidate);
            }
        }

        List<GroveBean> list = new ArrayList<>();
        list.addAll(map.values());
        return list;
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

    @Autowired
    public void setWioSettings(WioSettings wioSettings) {
        this.wioSettings = wioSettings;
    }

    @Autowired
    public void setGroveFactory(GroveFactory groveFactory) {
        this.groveFactory = groveFactory;
    }

    @Autowired
    public void setUserResource(UserResource userResource) {
        this.userResource = userResource;
    }

    @Autowired
    public void setWebSocketService(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }
}
