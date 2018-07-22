package world.esaka.auth.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@Configuration
@Order(4)
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class EsakaRepresentationConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


}
