package ru.practicum.explorewithme.clients;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

public class PublicClient extends BaseClient {

    private static final String EVENT = "/events";
    private static final String COMPILATIONS = "/events";

    public PublicClient(RestTemplate rest) {
        super(rest);
    }

    public ResponseEntity<Object> getEvents(Map<String, Object> parameters) {
        return get(EVENT, parameters);
    }

    public ResponseEntity<Object> getEventById(long id) {
        return get(EVENT, id);
    }

    public ResponseEntity<Object> getCompilations(Map<String, Object> parameters) {
        return get(COMPILATIONS, parameters);
    }
    public ResponseEntity<Object> getCompilationById(long id) {
        return get(COMPILATIONS, id);
    }
//
//    public ResponseEntity<Object> getEventById(int eventId) {
//
//    }
//
//    public ResponseEntity<Object> getCompilations(boolean pinned, int from, int size) {
//
//    }
//
//    public ResponseEntity<Object> getCompilationById(long compilationId) {
//
//    }
//
//    public ResponseEntity<Object> getCategoryById(Long categoryId) {
//
//    }
//
//    public ResponseEntity<Object> getCategories(int from, int size) {
//
//    }
}
