package ru.practicum.explorewithme.clients.server.priv;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.explorewithme.clients.BaseClient;
import ru.practicum.explorewithme.models.event.NewEventDto;
import ru.practicum.explorewithme.models.event.UpdateEventRequest;

import java.util.Map;

public class PrivateClient extends BaseClient {
    public PrivateClient(RestTemplate rest) {
        super(rest);
    }

    private static final String EVENTS = "/events";
    private static final String REQUESTS = "/requests";

    public ResponseEntity<Object> getEventsByOwnerId(long userId, int from, int size) {
        Map<String, Object> param = Map.of(
                "from", from,
                "size", size
        );
        String queryParam = "?from={from}&size={size}";
        return get("/" + userId + EVENTS + queryParam, param);
    }

    public ResponseEntity<Object> updateEvent(Long userId, UpdateEventRequest request) {
        return patch("/" + userId + EVENTS, request);
    }

    public ResponseEntity<Object> addEvent(Long userId, NewEventDto newEventDto) {
        return post("/" + userId + EVENTS, newEventDto);
    }

    public ResponseEntity<Object> getEventByOwnerIdAndEventId(long userId, long eventId) {
        return get("/" + userId + EVENTS + "/" + eventId);
    }

    public ResponseEntity<Object> cancelEvent(long userId, long eventId) {
        return patch("/" + userId + EVENTS + "/" + eventId);
    }

    public ResponseEntity<Object> getEventRequests(long userId, long eventId) {
        return get("/" + userId + EVENTS + "/" + eventId + REQUESTS);
    }

    public ResponseEntity<Object> confirmRequestForEvent(long userId, long eventId, long reqId) {
        return patch("/" + userId + EVENTS + "/" + eventId + REQUESTS + "/" + reqId + "/confirm");
    }

    public ResponseEntity<Object> rejectRequestForEvent(long userId, long eventId, long reqId) {
        return patch("/" + userId + EVENTS + "/" + eventId + REQUESTS + "/" + reqId + "/reject");
    }

    public ResponseEntity<Object> getEventRequestsByUser(long userId) {
        return get("/" + userId + REQUESTS);
    }

    public ResponseEntity<Object> addNewRequestByUser(long userId, long eventId) {
        Map<String, Object> param = Map.of("eventId", eventId);
        String queryUri = "?eventId={eventId}";
        return post("/" + userId + REQUESTS + queryUri, param);
    }

    public ResponseEntity<Object> cancelUserRequest(long userId, long requestId) {
        return patch("/" + userId + REQUESTS + "/" + requestId + "/cancel");
    }

    public ResponseEntity<Object> getEventsWhereParticipant(long followerId, Long userId, Map<String, Object> param) {
        String queryUri = "?state={state}&start={start}&end={end}&available={available}&from={from}&size={size}";

        return get("/" + userId + "/events/participant" + queryUri, followerId, param);
    }

    public ResponseEntity<Object> getEventsWhereCreator(long followerId, Long userId, Map<String, Object> param) {
        String queryUri = "?state={state}&start={start}&end={end}&available={available}&from={from}&size={size}";

        return get("/" + userId + "/events/creator" + queryUri, followerId, param);
    }

    public ResponseEntity<Object> addGroupToRequest(Long userId, Long requestId, Long group) {
        Map<String, Object> param = Map.of("group", group);
        return patch("/" + userId + "/requests/" + requestId + "?group={group}", param);
    }

    public ResponseEntity<Object> deleteGroupFromRequest(Long userId, Long requestId, Long group) {
        Map<String, Object> param = Map.of("group", group);
        return delete("/" + userId + "/requests/" + requestId + "?group={group}", param);
    }
}
