package nl.openweb.iot.wio.db;


import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import nl.openweb.iot.wio.rest.NodesResource;

@Data
@NoArgsConstructor
public class NodeBean {

    private String name;
    private String nodeKey;
    private String nodeSn;
    private String dataXServer;
    private String board;

    private List<GroveBean> groves;
    private Boolean initialized = false;

    public NodeBean(NodesResource.ListResponse.Node node) {
        this.name = node.getName();
        this.nodeKey = node.getNodeKey();
        this.nodeSn = node.getNodeSn();
        this.dataXServer = node.getDataXServer();
        this.board = node.getBoard();
    }


}
