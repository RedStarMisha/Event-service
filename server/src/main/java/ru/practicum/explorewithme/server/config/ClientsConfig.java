package ru.practicum.explorewithme.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.explorewithme.clients.stat.StatClient;

@Configuration
public class ClientsConfig {

    @Value("${stats-server.url}")
    private String statsUrl;

    private RestTemplateBuilder builder;

    @Autowired
    public ClientsConfig(RestTemplateBuilder builder) {
        this.builder = builder;
    }

    @Bean
    public StatClient makeStatsClient() {
        RestTemplate template = builder
                    .uriTemplateHandler(new DefaultUriBuilderFactory(statsUrl))  //фабрика для построения URI
                    .requestFactory(HttpComponentsClientHttpRequestFactory::new)  //фабрика для создания HTTPRequest
                    .build();

        return new StatClient(template);
    }
}
