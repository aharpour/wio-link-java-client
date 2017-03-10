package nl.openweb.iot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class KibanaGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(KibanaGatewayApplication.class, args);
	}
}
