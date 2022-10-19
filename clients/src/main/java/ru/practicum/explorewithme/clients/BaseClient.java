package ru.practicum.explorewithme.clients;

import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

public class BaseClient {
    private RestTemplate rest;

    public BaseClient(RestTemplate rest) {
        this.rest = rest;
    }

    public <T>ResponseEntity<Object> post(String path, @Nullable Map<String, Object> parameters, T body) {
        return makeReqAndGetResp(path, HttpMethod.POST, parameters, body);
    }

    public <T> ResponseEntity<Object> get(String url, Map<String, Object> parameters) {
        UriComponents uri = UriComponentsBuilder.newInstance().path(url).build();
        return get(uri.expand(parameters).getPath(), parameters, null);
    }

    public <T> ResponseEntity<Object> get(String url, long userId) {
        return get(url + "/" + userId, null, null);
    }

    public <T> ResponseEntity<Object> get(String path, @Nullable Map<String, Object> parameters, T body) {
        return makeReqAndGetResp(path, HttpMethod.GET, parameters, body);
    }

    private <T>ResponseEntity<Object> makeReqAndGetResp(String path, HttpMethod method,
                                                        @Nullable Map<String, Object> parameters, @Nullable T body) {
        HttpEntity<T> requestEntity = new HttpEntity<>(body, makeHeaders());

        ResponseEntity<Object> response;
        try {
            if (parameters != null) {
                response = rest.exchange(path, method, requestEntity, Object.class, parameters);
            } else {
                response = rest.exchange(path, method, requestEntity, Object.class);
            }
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsByteArray());
        }
        return response;
    }

    protected HttpHeaders makeHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }


}
