package world.esaka.client.auth.impl;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import world.esaka.client.auth.api.UserClient;
import world.esaka.client.auth.model.UserView;

import java.net.URI;
import java.util.ArrayList;

public class UserClientDefaultImpl implements UserClient {

    public UserView identificateByToken(String token) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Token", token);
        RequestEntity<String> httpEntity = new RequestEntity<String>("", headers, HttpMethod.GET, URI.create("http://localhost:8079/auth/user/current"));
        ArrayList<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        restTemplate.setMessageConverters(messageConverters);
        ResponseEntity<UserView> result = restTemplate.exchange(httpEntity, UserView.class);
        return result.getBody();
    }
}
