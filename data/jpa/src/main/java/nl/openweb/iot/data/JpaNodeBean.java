package nl.openweb.iot.data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import nl.openweb.iot.wio.db.GroveBean;
import nl.openweb.iot.wio.db.NodeBean;

@Data
@NoArgsConstructor
@Entity(name = "node")
public class JpaNodeBean {

    @Id
    private String nodeSn;
    private String name;
    private String nodeKey;
    private String dataXServer;
    private String board;

    @JsonIgnore
    @OneToMany(mappedBy="node", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JpaGroveBean> groves;
    private Boolean initialized = false;

    public JpaNodeBean(NodeBean nodeBean) {
        this.nodeKey = nodeBean.getNodeKey();
        this.nodeSn = nodeBean.getNodeSn();
        this.name = nodeBean.getName();
        this.dataXServer = nodeBean.getDataXServer();
        this.board = nodeBean.getBoard();
        this.initialized = nodeBean.getInitialized();
        List<JpaGroveBean> list = new ArrayList<>();
        List<GroveBean> groves = nodeBean.getGroves();
        if (groves != null) {
            for (GroveBean grove : groves) {
                JpaGroveBean groveBean = new JpaGroveBean(grove);
                groveBean.setNode(this);
                list.add(groveBean);
            }
        }
        this.groves = list;
    }

    public NodeBean toNodeBean() {
        NodeBean nodeBean = new NodeBean();
        nodeBean.setNodeSn(nodeSn);
        nodeBean.setName(name);
        nodeBean.setNodeKey(nodeKey);
        nodeBean.setDataXServer(dataXServer);
        nodeBean.setBoard(board);
        nodeBean.setInitialized(initialized);
        List<GroveBean> list = new ArrayList<>();
        if (groves != null) {
            for (JpaGroveBean grove : groves) {
                list.add(grove.toGroveBean());
            }
        }
        nodeBean.setGroves(list);
        return nodeBean;
    }

}
