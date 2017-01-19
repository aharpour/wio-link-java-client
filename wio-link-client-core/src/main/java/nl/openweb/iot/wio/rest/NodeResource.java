package nl.openweb.iot.wio.rest;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.Data;
import nl.openweb.iot.wio.WioException;

@FeignClient(name = "node-resource", url = "${wio.base-url}/v1/node", configuration = WioConfiguration.class)
public interface NodeResource {

    @RequestMapping(method = RequestMethod.GET, value = "/.well-known")
    NodeInfo getNodeInfo(@RequestParam("access_token") String nodeToken) throws WioException;

    @RequestMapping(method = RequestMethod.GET, value = "/{groveInstanceName}/{propertyName}")
    Map<String, String> readProperty(@RequestParam("access_token") String nodeToken, @PathVariable("groveInstanceName") String groveInstanceName, @PathVariable("propertyName") String propertyName) throws WioException;

    @RequestMapping(method = RequestMethod.GET, value = "/{groveInstanceName}/{propertyName}/{arg1}")
    Map<String, String> readProperty(@RequestParam("access_token") String nodeToken, @PathVariable("groveInstanceName") String groveInstanceName, @PathVariable("propertyName") String propertyName, @PathVariable("arg1") String arg1) throws WioException;

    @RequestMapping(method = RequestMethod.GET, value = "/{groveInstanceName}/{propertyName}/{arg1}/{arg2}")
    Map<String, String> readProperty(@RequestParam("access_token") String nodeToken, @PathVariable("groveInstanceName") String groveInstanceName, @PathVariable("propertyName") String propertyName, @PathVariable("arg1") String arg1, @PathVariable("arg2") String arg2) throws WioException;

    @RequestMapping(method = RequestMethod.GET, value = "/{groveInstanceName}/{propertyName}/{arg1}/{arg2}/{arg3}")
    Map<String, String> readProperty(@RequestParam("access_token") String nodeToken, @PathVariable("groveInstanceName") String groveInstanceName, @PathVariable("propertyName") String propertyName, @PathVariable("arg1") String arg1, @PathVariable("arg2") String arg2, @PathVariable("arg3") String arg3) throws WioException;

    @RequestMapping(method = RequestMethod.GET, value = "/{groveInstanceName}/{propertyName}/{arg1}/{arg2}/{arg3}/{arg4}")
    Map<String, String> readProperty(@RequestParam("access_token") String nodeToken, @PathVariable("groveInstanceName") String groveInstanceName, @PathVariable("propertyName") String propertyName, @PathVariable("arg1") String arg1, @PathVariable("arg2") String arg2, @PathVariable("arg3") String arg3, @PathVariable("arg4") String arg4) throws WioException;

    @RequestMapping(method = RequestMethod.POST, value = "/{groveInstanceName}/{propertyName}")
    WriteResponse writeProperty(@RequestParam("access_token") String nodeToken, @PathVariable("groveInstanceName") String groveInstanceName, @PathVariable("propertyName") String propertyName) throws WioException;

    @RequestMapping(method = RequestMethod.POST, value = "/{groveInstanceName}/{propertyName}/{arg1}")
    WriteResponse writeProperty(@RequestParam("access_token") String nodeToken, @PathVariable("groveInstanceName") String groveInstanceName, @PathVariable("propertyName") String propertyName, @PathVariable("arg1") String arg1) throws WioException;

    @RequestMapping(method = RequestMethod.POST, value = "/{groveInstanceName}/{propertyName}/{arg1}/{arg2}")
    WriteResponse writeProperty(@RequestParam("access_token") String nodeToken, @PathVariable("groveInstanceName") String groveInstanceName, @PathVariable("propertyName") String propertyName, @PathVariable("arg1") String arg1, @PathVariable("arg2") String arg2) throws WioException;

    @RequestMapping(method = RequestMethod.POST, value = "/{groveInstanceName}/{propertyName}/{arg1}/{arg2}/{arg3}")
    WriteResponse writeProperty(@RequestParam("access_token") String nodeToken, @PathVariable("groveInstanceName") String groveInstanceName, @PathVariable("propertyName") String propertyName, @PathVariable("arg1") String arg1, @PathVariable("arg2") String arg2, @PathVariable("arg3") String arg3) throws WioException;

    @RequestMapping(method = RequestMethod.POST, value = "/{groveInstanceName}/{propertyName}/{arg1}/{arg2}/{arg3}/{arg4}")
    WriteResponse writeProperty(@RequestParam("access_token") String nodeToken, @PathVariable("groveInstanceName") String groveInstanceName, @PathVariable("propertyName") String propertyName, @PathVariable("arg1") String arg1, @PathVariable("arg2") String arg2, @PathVariable("arg3") String arg3, @PathVariable("arg4") String arg4) throws WioException;

    @RequestMapping(method = RequestMethod.POST, value = "/v1/node/pm/sleep/{sleepTime}")
    SleepResponse sleep(@RequestParam("access_token") String nodeToken, @PathVariable("sleepTime") String sleepTimeSec) throws WioException;

    @Data
    public static class NodeInfo {
        private String name;
        @JsonProperty("well_known")
        private List<String> calls;
    }

    @Data
    public static class WriteResponse {
        private String result;
    }

    @Data
    public static class SleepResponse {
        private String result;
    }
}
