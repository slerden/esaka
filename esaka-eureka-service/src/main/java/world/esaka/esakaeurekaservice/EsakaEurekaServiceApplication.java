package world.esaka.esakaeurekaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EsakaEurekaServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EsakaEurekaServiceApplication.class, args);
    }
}
