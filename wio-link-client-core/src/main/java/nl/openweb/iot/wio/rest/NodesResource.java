package nl.openweb.iot.wio.rest;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.Data;
import nl.openweb.iot.wio.WioException;

@FeignClient(name = "nodes-resource", url = "${wio.base-url}/v1/nodes", configuration = WioRestConfiguration.class)
public interface NodesResource {

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    CreateResponse create(@RequestParam("access_token") String userToken, @RequestParam("name") String name, @RequestParam("board") String board) throws WioException;

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    ListResponse list(@RequestParam("access_token") String userToken) throws WioException;

    @RequestMapping(method = RequestMethod.POST, value = "/rename")
    RenameResponse rename(@RequestParam("access_token") String userToken, @RequestParam("node_sn") String nodeSn, @RequestParam("name") String newName) throws WioException;

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    DeleteResponse delete(@RequestParam("access_token") String userToken, @RequestParam("node_sn") String nodeSn) throws WioException;

    @Data
    public static class CreateResponse {
        @JsonProperty("node_key")
        private String nodeKey;
        @JsonProperty("node_sn")
        private String nodeSn;
    }

    @Data
    public static class ListResponse {
        private List<Node> nodes;

        @Data
        public static class Node {
            private String name;
            @JsonProperty("node_key")
            private String nodeKey;
            @JsonProperty("node_sn")
            private String nodeSn;
            @JsonProperty("dataxserver")
            private String dataXServer;
            private String board;
            private boolean online;
        }
    }

    @Data
    public static class RenameResponse {
        private String result;
    }

    @Data
    public static class DeleteResponse {
        private String result;
    }
}
