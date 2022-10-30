package ru.practicum.explorewithme.clients.stat;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.explorewithme.clients.BaseClient;
import ru.practicum.explorewithme.models.statistics.EndpointHit;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static java.util.stream.Collectors.joining;

public class StatClient extends BaseClient {

    public StatClient(RestTemplate rest) {
        super(rest);
    }

    public ResponseEntity<Object> addHit(EndpointHit endpointHit) {
        return post("/hit", endpointHit);
    }

    public ResponseEntity<Object> getStats(LocalDateTime start, LocalDateTime end, String[] uris, Boolean unique) {

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
}
