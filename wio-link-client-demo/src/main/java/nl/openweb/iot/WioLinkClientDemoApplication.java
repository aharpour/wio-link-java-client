package nl.openweb.iot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.stereotype.Component;

import nl.openweb.iot.wio.rest.UserResource;

@SpringBootApplication
@EnableFeignClients
public class WioLinkClientDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WioLinkClientDemoApplication.class, args);
    }
}

@Component
class TestRunner implements CommandLineRunner {

    @Autowired
    private UserResource userResource;

    @Override
    public void run(String... args) throws Exception {
        UserResource.Login login = userResource.login("test1234asdf23@gmail.com", "testqwasdf12345");
        System.out.println(login.getToken());
        System.out.println(login.getUserId());
        UserResource.Create newPassword = userResource.changePassword(login.getToken(), "testqwasdf123456");
        UserResource.Login login1 = userResource.login("test1234asdf23@gmail.com", "testqwasdf123456");
        System.out.println(login.getToken());
        System.out.println(login.getUserId());
        userResource.login("test1234asdf23@gmail.com", "testqwasdf12345");
    }
}

