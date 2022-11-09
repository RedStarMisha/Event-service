package ru.practicum.explorewithme.clients;

import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BaseClient {
    private final RestTemplate rest;

    public BaseClient(RestTemplate rest) {
        this.rest = rest;
    }

    protected <T> ResponseEntity<Object> post(String path, T body) {
        return post(path, null, null, body);
    }

    protected <T> ResponseEntity<Object> post(String path, Long userId, T body) {
        return post(path, userId, null, body);
    }

    protected ResponseEntity<Object> post(String path, Map<String, Object> parameters) {
        return post(path, null, parameters, null);
    }

    protected <T> ResponseEntity<Object> post(String path, @Nullable Long userId, @Nullable Map<String, Object> parameters,
                                              T body) {
        return makeReqAndGetResp(path, HttpMethod.POST, userId, parameters, body);
    }

    protected ResponseEntity<Object> get(String url) {
        return get(url, null, null);
    }

    protected ResponseEntity<Object> get(String url, long userId) {
        return get(url, userId, null);
    }

    protected ResponseEntity<Object> get(String url, Map<String, Object> parameters) {
        return get(url, null, parameters);
    }

    protected ResponseEntity<Object> get(String path, @Nullable Long userId, @Nullable Map<String, Object> parameters) {
        return makeReqAndGetResp(path, HttpMethod.GET, userId, parameters, null);
    }

    protected <T> ResponseEntity<Object> patch(String path, T body) {
        return patch(path, null, null, body);
    }

    protected ResponseEntity<Object> patch(String path, Long userId, Map<String, Object> parameters) {
        return patch(path, userId, parameters, null);
    }

    protected ResponseEntity<Object> patch(String path, Long userId) {
        return patch(path, userId, null, null);
    }

    protected ResponseEntity<Object> patch(String path) {
        return patch(path, null, null, null);
    }

    protected <T> ResponseEntity<Object> patch(String path, long userId, T body) {
        return patch(path, userId, null, body);
    }

    protected ResponseEntity<Object> patch(String path, Map<String, Object> parameters) {
        return patch(path, null, parameters, null);
    }

    protected <T> ResponseEntity<Object> patch(String path, @Nullable Long userId, @Nullable Map<String, Object> parameters,
                                               @Nullable T body) {
        return makeReqAndGetResp(path, HttpMethod.PATCH, userId, parameters, body);
    }

    protected <T> ResponseEntity<Object> put(String path, @Nullable T body) {
        return makeReqAndGetResp(path, HttpMethod.PUT, null, null, body);
    }

    protected ResponseEntity<Object> delete(String path) {
        return delete(path, null);
    }

    protected ResponseEntity<Object> delete(String path, @Nullable Map<String, Object> parameters) {
        return makeReqAndGetResp(path, HttpMethod.DELETE, null, parameters, null);
    }

    private <T> ResponseEntity<Object> makeReqAndGetResp(String path, HttpMethod method, Long userId,
                                                         @Nullable Map<String, Object> parameters, @Nullable T body) {
        HttpEntity<T> requestEntity = new HttpEntity<>(body, makeHeaders(userId));

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
        return prepareGatewayResponse(response);
    }

    protected HttpHeaders makeHeaders(Long userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        Optional.ofNullable(userId).ifPresent(id -> headers.set("X-EWM-User-Id", String.valueOf(userId)));
        return headers;
    }

    private static ResponseEntity<Object> prepareGatewayResponse(ResponseEntity<Object> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        }

        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());

        if (response.hasBody()) {
            return responseBuilder.body(response.getBody());
        }

        return responseBuilder.build();
    }
}
