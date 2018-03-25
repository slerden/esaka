package world.esaka.esakaeurekaclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class EsakaFilestoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(EsakaFilestoreApplication.class, args);
    }
}
