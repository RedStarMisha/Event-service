package ru.practicum.explorewithme.priv;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.clients.PrivateClient;
import ru.practicum.explorewithme.models.event.NewEventDto;
import ru.practicum.explorewithme.models.event.UpdateEventRequest;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping("/users")
@AllArgsConstructor(onConstructor_ = @Autowired)
@Validated
public class PrivateController {

    private final PrivateClient client;

    @GetMapping("/{userId}/events")
    public ResponseEntity<Object> getEventsByOwnerId(@PathVariable(name = "userId") Long userId,
                                             @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                             @RequestParam(name = "size", defaultValue = "10") @Positive int size) {
        return client.getEventsByOwnerId(userId, from, size);
    }

    @PatchMapping("/{userId}/events")
    public ResponseEntity<Object> updateEvent(@PathVariable(name = "userId") @Positive Long userId,
                                              @RequestBody @Valid UpdateEventRequest request) {
        return client.updateEvent(userId, request);
    }

    @PostMapping("/{userId}/events")
    public ResponseEntity<Object> addEvent(@PathVariable(name = "userId") @Positive Long userId,
                                           @RequestBody @Valid NewEventDto newEventDto) {
        return client.addEvent(userId, newEventDto);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public ResponseEntity<Object> getEventByOwnerIdAndEventId(@PathVariable(name = "userId") @Positive Long userId,
                                                     @PathVariable(name = "eventId") @Positive Long eventId) {
        return client.getEventByOwnerIdAndEventId(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public ResponseEntity<Object> cancelEventByOwner(@PathVariable(name = "userId") @Positive Long userId,
                                              @PathVariable(name = "eventId") @Positive Long eventId) {
        return client.cancelEvent(userId, eventId);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public ResponseEntity<Object> getEventRequests(@PathVariable(name = "userId") @Positive Long userId,
                                                   @PathVariable(name = "eventId") @Positive Long eventId) {
        return client.getEventRequests(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/confirm")
    public ResponseEntity<Object> confirmRequestForEvent(@PathVariable(name = "userId") @Positive Long userId,
                                                         @PathVariable(name = "eventId") @Positive Long eventId,
                                                         @PathVariable(name = "reqId") @Positive Long reqId) {
        return client.confirmRequestForEvent(userId, eventId, reqId);
    }
    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/reject")
    public ResponseEntity<Object> rejectRequestForEvent(@PathVariable(name = "userId") @Positive Long userId,
                                                        @PathVariable(name = "eventId") @Positive Long eventId,
                                                        @PathVariable(name = "reqId") @Positive Long reqId) {
        return client.rejectRequestForEvent(userId, eventId, reqId);
    }

    @GetMapping("/{userId}/requests")
    public ResponseEntity<Object> getEventRequestsByUser(@PathVariable(name = "userId") @Positive Long userId) {
        return client.getEventRequestsByUser(userId);
    }

    @PostMapping("/{userId}/requests")
    public ResponseEntity<Object> addNewRequestByUser(@PathVariable(name = "userId") @Positive Long userId,
                                                      @PathVariable(name = "eventId") @Positive Long eventId) {
        return client.addNewRequestByUser(userId, eventId);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ResponseEntity<Object> cancelUserRequest(@PathVariable(name = "userId") @Positive Long userId,
                                                    @PathVariable(name = "requestId") @Positive Long requestId) {
        return client.cancelUserRequest(userId, requestId);
    }

}