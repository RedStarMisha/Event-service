package ru.practicum.explorewithme.clients.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.explorewithme.clients.BaseClient;
import ru.practicum.explorewithme.models.event.AdminUpdateEventRequest;

import java.util.Map;

public class EventClient extends BaseClient {

    public EventClient(RestTemplate rest) {
        super(rest);
    }

    public ResponseEntity<Object> getEvents(Map<String, Object> param) {
        return get("", param);
    }

    public ResponseEntity<Object> updateEvent(long eventId, AdminUpdateEventRequest updateEventRequest) {
        return patch("/" + eventId, updateEventRequest);
    }

    public ResponseEntity<Object> publishEvent(long eventId) {
        return patch("/" + eventId + "/publish");
    }

    public ResponseEntity<Object> rejectEvent(long eventId) {
        return patch("/" + eventId + "/reject");
    }
}
