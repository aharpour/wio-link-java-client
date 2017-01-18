package nl.openweb.iot.wio.rest;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.Data;

@FeignClient(name = "node-resource", url = "${wio.base.url}/v1/nodes", configuration = WioConfiguration.class)
public interface NodeResource {

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    CreateResponse create(@RequestParam("access_token") String accessToken, @RequestParam("name") String name, @RequestParam("board") String board) throws WioException;

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    ListResponse list(@RequestParam("access_token") String accessToken) throws WioException;

    @RequestMapping(method = RequestMethod.POST, value = "/rename")
    RenameResponse rename(@RequestParam("access_token") String accessToken, @RequestParam("node_sn") String nodeSn, @RequestParam("name") String newName) throws WioException;

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    DeleteResponse delete(@RequestParam("access_token") String accessToken, @RequestParam("node_sn") String nodeSn) throws WioException;

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
