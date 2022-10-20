package ru.practicum.explorewithme.privatenode;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.clients.PrivateClient;
import ru.practicum.explorewithme.clients.model.NewEventDto;
import ru.practicum.explorewithme.clients.model.UpdateEventRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@AllArgsConstructor(onConstructor_ = @Autowired)
public class PrivateController {

    private final PrivateClient client;

    @GetMapping("/{userId}/events")
    public ResponseEntity<Object> getEventsByOwnerId(@PathVariable(name = "userId") Long userId,
                                                     @RequestParam(name = "from", defaultValue = "0") int from,
                                                     @RequestParam(name = "size", defaultValue = "10") int size) {
        return client.getEventsByOwnerId(userId, from, size);
    }

    @PatchMapping("/{userId}/events")
    public ResponseEntity<Object> updateEvent(@PathVariable(name = "userId") Long userId,
                                              @RequestBody @Valid UpdateEventRequest request) {
        return client.updateEvent(userId, request);
    }

    @PostMapping("/{userId}/events")
    public ResponseEntity<Object> addEvent(@PathVariable(name = "userId") Long userId,
                                           @RequestBody @Valid NewEventDto newEventDto) {
        return client.addEvent(userId, newEventDto);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public ResponseEntity<Object> getEventByOwnerIdAndEventId(@PathVariable(name = "userId") long userId,
                                                     @PathVariable(name = "eventId") long eventId) {
        return client.getEventByOwnerIdAndEventId(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public ResponseEntity<Object> cancelEventByOwner(@PathVariable(name = "userId") long userId,
                                              @PathVariable(name = "eventId") long eventId) {
        return client.cancelEvent(userId, eventId);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public ResponseEntity<Object> getEventRequests(@PathVariable(name = "userId") String userId,
                                                   @PathVariable(name = "eventId") String eventId) {
        return client.getEventRequests(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/confirm")
    public ResponseEntity<Object> confirmRequestForEvent(@PathVariable(name = "userId") long userId,
                                                         @PathVariable(name = "eventId") long eventId,
                                                         @PathVariable(name = "reqId") long reqId) {
        return client.confirmRequestForEvent(userId, eventId, reqId);
    }
    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/reject")
    public ResponseEntity<Object> rejectRequestForEvent(@PathVariable(name = "userId") long userId,
                                                         @PathVariable(name = "eventId") long eventId,
                                                         @PathVariable(name = "reqId") long reqId) {
        return client.rejectRequestForEvent(userId, eventId, reqId);
    }

    @GetMapping("/{userId}/requests")
    public ResponseEntity<Object> getEventRequestsByUser(@PathVariable(name = "userId") long userId) {
        return client.getEventRequestsByUser(userId);
    }

    @PostMapping("/{userId}/requests")
    public ResponseEntity<Object> addNewRequestByUser(@PathVariable(name = "userId") long userId,
                                                      @RequestParam(name = "eventId") long eventId) {
        return client.addNewRequestByUser(userId, eventId);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ResponseEntity<Object> cancelUserRequest(@PathVariable(name = "userId") long userId,
                                                    @PathVariable(name = "requestId") long requestId) {
        return client.cancelUserRequest(userId, requestId);
    }

}
