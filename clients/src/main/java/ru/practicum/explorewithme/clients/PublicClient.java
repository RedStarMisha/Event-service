package ru.practicum.explorewithme.clients;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


import java.util.Map;

public class PublicClient extends BaseClient {

    private static final String EVENTS = "/events";
    private static final String COMPILATIONS = "/compilations";
    private static final String CATEGORIES = "/categories";

    public PublicClient(RestTemplate rest) {
        super(rest);
    }

    public ResponseEntity<Object> getEvents(Map<String, Object> parameters) {
        return get(EVENTS, parameters);
    }

    public ResponseEntity<Object> getEventById(long id) {
        return get(EVENTS + "/" + id);
    }

    public ResponseEntity<Object> getCompilations(boolean pinned, int from, int size) {
        Map<String, Object> param = Map.of(
                "pinned", pinned,
                "from", from,
                "size", size);
        return get(COMPILATIONS, param);
    }
    public ResponseEntity<Object> getCompilationById(long id) {
        return get(COMPILATIONS + "/" + id);
    }

    public ResponseEntity<Object> getCategories(int from, int size) {
        Map<String, Object> param = Map.of(
                "from", from,
                "size", size);
        return get(CATEGORIES, param);
    }

    public ResponseEntity<Object> getCategoryById(long categoryId) {
        return get(CATEGORIES + "/" + categoryId);
    }


}
