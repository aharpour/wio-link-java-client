package nl.openweb.iot.wio.db;


import java.util.List;

import org.springframework.data.annotation.Transient;

import lombok.Data;
import lombok.NoArgsConstructor;
import nl.openweb.iot.wio.rest.NodeResource;

@Data
@NoArgsConstructor
public class NodeBean {
    private String name;
    private String nodeKey;
    private String nodeSn;
    private String dataXServer;
    private String board;

    public NodeBean(String name, String nodeKey, String nodeSn, String dataXServer, String board) {
        this.name = name;
        this.nodeKey = nodeKey;
        this.nodeSn = nodeSn;
        this.dataXServer = dataXServer;
        this.board = board;
    }

    private List<GroveBean> groves;

    @Transient
    private NodeResource nodeResource;

}
