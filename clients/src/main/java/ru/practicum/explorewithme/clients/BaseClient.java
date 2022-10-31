package ru.practicum.explorewithme.clients;

import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;

public class BaseClient {
    private RestTemplate rest;

    public BaseClient(RestTemplate rest) {
        this.rest = rest;
    }

    protected <T>ResponseEntity<Object> post(String path, T body) {
        return post(path, null, body);
    }
    protected ResponseEntity<Object> post(String path, Map<String, Object> parameters) {
        return post(path, parameters, null);
    }
    protected <T>ResponseEntity<Object> post(String path, @Nullable Map<String, Object> parameters, T body) {
        return makeReqAndGetResp(path, HttpMethod.POST, parameters, body);
    }

    protected ResponseEntity<Object> get(String url) {
        return get(url, null);
    }

    protected ResponseEntity<Object> get(String path, @Nullable Map<String, Object> parameters) {
        return makeReqAndGetResp(path, HttpMethod.GET, parameters, null);
    }

    protected <T> ResponseEntity<Object> patch(String path, T body) {
        return patch(path, null, body);
    }
    protected <T> ResponseEntity<Object> patch(String path) {
        return patch(path, null, null);
    }

    protected <T> ResponseEntity<Object> patch(String path, @Nullable Map<String, Object> parameters, @Nullable T body) {
        return makeReqAndGetResp(path, HttpMethod.PATCH, parameters, body);
    }

    protected <T> ResponseEntity<Object> put(String path, @Nullable T body) {
        return makeReqAndGetResp(path, HttpMethod.PUT, null, body);
    }

    protected <T> ResponseEntity<Object> delete(String path) {
        return delete(path, null);
    }

    protected <T> ResponseEntity<Object> delete(String path, @Nullable Map<String, Object> parameters) {
        return makeReqAndGetResp(path, HttpMethod.DELETE, parameters, null);
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
