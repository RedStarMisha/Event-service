package ru.practicum.explorewithme.clients.server;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.explorewithme.clients.BaseClient;


import java.util.HashMap;
import java.util.Map;

public class PublicClient extends BaseClient {

    private static final String EVENTS = "/events";
    private static final String COMPILATIONS = "/compilations";
    private static final String CATEGORIES = "/categories";

    public PublicClient(RestTemplate rest) {
        super(rest);
    }

    public ResponseEntity<Object> getEvents(Map<String, Object> parameters) {
        String queryParam = "?text={text}&categories={categories}&paid={paid}&rangeStart={rangeStart}&" +
                "rangeEnd={rangeEnd}&available={available}&sort={sort}&from={from}&size={size}";
        return get(EVENTS + queryParam, parameters);
    }

    public ResponseEntity<Object> getEventById(long id) {
        return get(EVENTS + "/" + id);
    }

    public ResponseEntity<Object> getCompilations(Boolean pinned, int from, int size) {
        Map<String, Object> param = new HashMap<>();
        param.put("pinned", pinned);
        param.put("from", from);
        param.put("size", size);

        String queryParam = "?pinned={pinned}&from={from}&size={size}";
        return get(COMPILATIONS + queryParam, param);
    }

    public ResponseEntity<Object> getCompilationById(long id) {
        return get(COMPILATIONS + "/" + id);
    }

    public ResponseEntity<Object> getCategories(int from, int size) {
        Map<String, Object> param = Map.of(
                "from", from,
                "size", size);
        String queryParam = "?from={from}&size={size}";
        return get(CATEGORIES + queryParam, param);
    }

    public ResponseEntity<Object> getCategoryById(long categoryId) {
        return get(CATEGORIES + "/" + categoryId);
    }


}
