package nl.openweb.iot.wio.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.Data;

@FeignClient(name = "user-resource", url = "${wio.base.url}/v1/user", configuration = WioConfiguration.class)
public interface UserResource {

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    Login login(@RequestParam("email") String email, @RequestParam("password") String password) throws WioException;

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    Create create(@RequestParam("email") String email, @RequestParam("password") String password) throws WioException;

    @RequestMapping(method = RequestMethod.POST, value = "/changepassword")
    Create changePassword(@RequestParam("access_token") String accessToken, @RequestParam("password") String newPassword) throws WioException;


    @Data
    public static class Login {
        private String token;
        @JsonProperty("user_id")
        private String userId;
    }

    @Data
    public static class Create {
        private String token;
    }


}
