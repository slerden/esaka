package world.esaka.filestore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.hateoas.config.EnableEntityLinks;

@EnableDiscoveryClient
@SpringBootApplication
@EnableEntityLinks
public class EsakaFilestoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(EsakaFilestoreApplication.class, args);
    }
}
