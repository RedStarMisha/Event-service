package ru.practicum.explorewithme.clients.stat;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.explorewithme.clients.BaseClient;
import ru.practicum.explorewithme.models.statistics.EndpointHit;

import java.time.LocalDateTime;
import java.util.Map;

public class StatClient extends BaseClient {

    public StatClient(RestTemplate rest) {
        super(rest);
    }

    public ResponseEntity<Object> addHit(EndpointHit endpointHit) {
        return post("/hit", endpointHit);
    }

    public ResponseEntity<Object> getStats(LocalDateTime start, LocalDateTime end, String[] uris, Boolean unique) {

        Map<String, Object> param = Map.of(
                "start", start,
                "end", end,
                "uris", uris,
                "unique", unique
        );
        return get("/stats", param);
    }
}
