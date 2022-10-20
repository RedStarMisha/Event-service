package ru.practicum.explorewithme.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.explorewithme.clients.admin.UserClient;

@Configuration
public class ClientsConfig {
    @Value("${main-server.url}")
    private String serverUrl;

    private RestTemplateBuilder builder;

    public ClientsConfig(RestTemplateBuilder builder) {
        this.builder = builder;
    }

    @Bean
    public UserClient makeUserClient() {
        String prefix = "/admin/users";
        return new UserClient(makeRestTemplate(serverUrl + prefix));
    }

    private RestTemplate makeRestTemplate(String prefix) {
        return builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + prefix))  //фабрика для построения URI
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)  //фабрика для создания HTTPRequest
                .build();
    }
}
