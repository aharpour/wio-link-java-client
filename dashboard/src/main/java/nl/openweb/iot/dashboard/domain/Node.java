package nl.openweb.iot.dashboard.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Node.
 */
@Entity
@Table(name = "node")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Node implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "node_sn", nullable = false)
    private String nodeSn;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "node_key", nullable = false)
    private String nodeKey;

    @Column(name = "data_x_server")
    private String dataXServer;

    @Column(name = "board")
    private String board;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNodeSn() {
        return nodeSn;
    }

    public Node nodeSn(String nodeSn) {
        this.nodeSn = nodeSn;
        return this;
    }

    public void setNodeSn(String nodeSn) {
        this.nodeSn = nodeSn;
    }

    public String getName() {
        return name;
    }

    public Node name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNodeKey() {
        return nodeKey;
    }

    public Node nodeKey(String nodeKey) {
        this.nodeKey = nodeKey;
        return this;
    }

    public void setNodeKey(String nodeKey) {
        this.nodeKey = nodeKey;
    }

    public String getDataXServer() {
        return dataXServer;
    }

    public Node dataXServer(String dataXServer) {
        this.dataXServer = dataXServer;
        return this;
    }

    public void setDataXServer(String dataXServer) {
        this.dataXServer = dataXServer;
    }

    public String getBoard() {
        return board;
    }

    public Node board(String board) {
        this.board = board;
        return this;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Node node = (Node) o;
        if (node.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, node.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Node{" +
            "id=" + id +
            ", nodeSn='" + nodeSn + "'" +
            ", name='" + name + "'" +
            ", nodeKey='" + nodeKey + "'" +
            ", dataXServer='" + dataXServer + "'" +
            ", board='" + board + "'" +
            '}';
    }
}
