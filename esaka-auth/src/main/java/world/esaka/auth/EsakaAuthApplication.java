package world.esaka.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@EnableEurekaClient
public class EsakaAuthApplication {


    public static void main(String[] args) {
        SpringApplication.run(EsakaAuthApplication.class, args);
    }
}
