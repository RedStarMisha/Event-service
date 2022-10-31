package ru.practicum.explorewithme.clients.stat;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ru.practicum.explorewithme.clients.BaseClient;
import ru.practicum.explorewithme.models.statistics.EndpointHit;
import ru.practicum.explorewithme.models.statistics.ViewStats;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.joining;

public class StatClient {

    private RestTemplate rest;

    public StatClient(RestTemplate rest) {
        this.rest = rest;
    }


    public ResponseEntity<EndpointHit> addHit(EndpointHit endpointHit) {
        return post("/hit", endpointHit);
    }

    public ResponseEntity<List<ViewStats>> getStats(LocalDateTime start, LocalDateTime end, String[] uris, Boolean unique) {

        Map<String, String> dateParam = Map.of(
                "start", formatDate(start),
                "end", formatDate(end)
        );
        Map<String, Object> otherParam = Map.of(
                "uris", uris,
                "unique", unique
        );
        String encodeDate = dateParam.keySet().stream().map(key -> {
                    try {
                        return key + "=" + encodeValue(encodeValue(dateParam.get(key)));
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                }).reduce((a, b) -> b + "&" + a).get();

        return get("/stats?" + encodeDate + "&uris={uris}&unique={unique}", otherParam);
    }

    private static String encodeValue(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    }

    private static String formatDate(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return date.format(formatter);
    }

    private ResponseEntity<List<ViewStats>> get(String path, Map<String, Object> parameters) {
        return rest.exchange(path, HttpMethod.GET, null, new ParameterizedTypeReference<>() {}, parameters);
    }

    private ResponseEntity<EndpointHit> post(String path, EndpointHit body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<EndpointHit> requestEntity = new HttpEntity<>(body, headers);
        return rest.exchange(path, HttpMethod.POST, requestEntity, EndpointHit.class);
    }
}
