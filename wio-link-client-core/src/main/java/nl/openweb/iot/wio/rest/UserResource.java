package nl.openweb.iot.wio.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.Data;
import nl.openweb.iot.wio.WioException;

@FeignClient(name = "user-resource", url = "${wio.base-url}/v1/user", configuration = WioConfiguration.class)
public interface UserResource {

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    LoginResponse login(@RequestParam("email") String email, @RequestParam("password") String password) throws WioException;

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    CreateResponse create(@RequestParam("email") String email, @RequestParam("password") String password) throws WioException;

    @RequestMapping(method = RequestMethod.POST, value = "/changepassword")
    CreateResponse changePassword(@RequestParam("access_token") String accessToken, @RequestParam("password") String newPassword) throws WioException;

    @RequestMapping(method = RequestMethod.POST, value = "/retrievepassword")
    ResetPasswordResponse resetPassword(@RequestParam("email") String email) throws WioException;


    @Data
    public static class LoginResponse {
        private String token;
        @JsonProperty("user_id")
        private String userId;
    }

    @Data
    public static class CreateResponse {
        private String token;
    }
    @Data
    public static class ResetPasswordResponse {
        private String result;
    }


}
