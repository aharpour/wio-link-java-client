package nl.openweb.iot.wio.rest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import lombok.AllArgsConstructor;
import lombok.Data;
import nl.openweb.iot.wio.WioException;

@Service
public class SeeedSsoResource {

    private RestTemplate restTemplate;

    private static final Pattern TOKEN_REGEX = Pattern.compile("\\btoken: (.*)\\b");
    private static final Pattern SSO_TOKEN_REGEX = Pattern.compile("\\bsso token: (.*)\\b");

    public SeeedSsoResource(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public LoginResponse login(String email, String password) throws WioException {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
        parameters.add("email", email);
        parameters.add("password", password);
        String response = restTemplate.postForObject("https://wio.seeed.io/login", parameters, String.class);

        String token = getToken(TOKEN_REGEX, response);
        String ssoToken = getToken(SSO_TOKEN_REGEX, response);
        if (StringUtils.isBlank(token)) {
            throw new WioException("Token was not found.");
        }
        return new LoginResponse(token, ssoToken);
    }

    private String getToken(Pattern regex, String response) {
        String result = null;
        Matcher matcher = regex.matcher(response);
        if (matcher.find()) {
            result = matcher.group(1);
        }
        return result;
    }

    @Data
    @AllArgsConstructor
    public static class LoginResponse {
        private String token;
        private String ssoToken;
    }
}
