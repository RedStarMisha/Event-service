package ru.practicum.explorewithme;

import org.springframework.web.client.RestTemplate;

public class BaseClient {
    private RestTemplate restTemplate;

    public BaseClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


}
