package ru.practicum.explorewithme.publicnode;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PublicClient {

    public ResponseEntity<Object> getEvents(QueryParameters queryParameters) {

    }

    public ResponseEntity<Object> getEventById(int eventId) {

    }

    public ResponseEntity<Object> getCompilations(boolean pinned, int from, int size) {

    }

    public ResponseEntity<Object> getCompilationById(long compilationId) {

    }

    public ResponseEntity<Object> getCategoryById(Long categoryId) {

    }

    public ResponseEntity<Object> getCategories(int from, int size) {

    }
}
