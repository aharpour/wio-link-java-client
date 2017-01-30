package nl.openweb.iot.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import nl.openweb.iot.wio.db.GroveBean;
import nl.openweb.iot.wio.db.NodeBean;

@Data
@NoArgsConstructor
@Document(indexName = "wio", type = "nodes")
public class ElasticNodeBean {

    @Id
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String nodeSn;
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String name;
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String nodeKey;
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String dataXServer;
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String board;
    @Field(type = FieldType.Nested)
    private List<ElasticGroveBean> groves;
    private Boolean initialized = false;

    public ElasticNodeBean(NodeBean nodeBean) {
        this.nodeKey = nodeBean.getNodeKey();
        this.nodeSn = nodeBean.getNodeSn();
        this.name = nodeBean.getName();
        this.dataXServer = nodeBean.getDataXServer();
        this.board = nodeBean.getBoard();
        this.initialized = nodeBean.getInitialized();
        List<ElasticGroveBean> list = new ArrayList<>();
        List<GroveBean> groves = nodeBean.getGroves();
        if (groves != null) {
            for (GroveBean grove : groves) {
                list.add(new ElasticGroveBean(grove));
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
        List<GroveBean> list = new ArrayList<>();
        if (groves != null) {
            for (ElasticGroveBean grove : groves) {
                list.add(grove.toGroveBean());
            }
        }
        nodeBean.setGroves(list);
        return nodeBean;
    }

}
