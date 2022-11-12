package ru.practicum.explorewithme.priv;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.clients.server.priv.PrivateClient;
import ru.practicum.explorewithme.exceptions.UnknownEnumElementException;
import ru.practicum.explorewithme.models.event.EventState;
import ru.practicum.explorewithme.models.event.NewEventDto;
import ru.practicum.explorewithme.models.event.UpdateEventRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.HashMap;
import java.util.Map;

import static ru.practicum.explorewithme.validation.ValidUtil.dateValidation;

@RestController
@RequestMapping("/users")
@AllArgsConstructor(onConstructor_ = @Autowired)
@Validated
public class PrivateController {

    private final PrivateClient client;

    @GetMapping("/{userId}/events")
    public ResponseEntity<Object> getEventsByOwnerId(@PathVariable(name = "userId") Long userId,
                                                     @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                                     @RequestParam(name = "size", defaultValue = "10") @Positive Integer size) {
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
    public ResponseEntity<Object> getEventRequestsByUser(@PathVariable(name = "userId") Long userId) {
        return client.getEventRequestsByUser(userId);
    }

    @PostMapping("/{userId}/requests")
    public ResponseEntity<Object> addNewRequestByUser(@PathVariable(name = "userId") Long userId,
                                                      @RequestParam(name = "eventId") @NotNull long eventId) {
        return client.addNewRequestByUser(userId, eventId);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ResponseEntity<Object> cancelUserRequest(@PathVariable(name = "userId") Long userId,
                                                    @PathVariable(name = "requestId") Long requestId) {
        return client.cancelUserRequest(userId, requestId);
    }

    @PatchMapping("/{userId}/requests/{requestId}")
    public ResponseEntity<Object> addGroupToRequest(@PathVariable(name = "userId") @Positive Long userId,
                                                    @PathVariable(name = "requestId") @Positive Long requestId,
                                                    @RequestParam(name = "group") Long group) {
        return client.addGroupToRequest(userId, requestId, group);
    }
    @DeleteMapping("/{userId}/requests/{requestId}")
    public ResponseEntity<Object> deleteGroupFromRequest(@PathVariable(name = "userId") @Positive Long userId,
                                                         @PathVariable(name = "requestId") @Positive Long requestId,
                                                         @RequestParam(name = "group") Long group) {
        return client.deleteGroupFromRequest(userId, requestId, group);
    }

    @GetMapping("/{userId}/events/participant")
    public ResponseEntity<Object> getEventsWhereParticipant(@RequestHeader("X-EWM-User-Id") long followerId,
                                                            @PathVariable(name = "userId") Long userId,
                                                            @RequestParam(name = "state") String stateString,
                                                            @RequestParam(name = "rangeStart", required = false) String start,
                                                            @RequestParam(name = "rangeEnd", required = false) String end,
                                                            @RequestParam(name = "available", required = false) Boolean available,
                                                            @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                                            @RequestParam(name = "size", defaultValue = "10") @Positive Integer size) {
        EventState state = EventState.from(stateString)
                .orElseThrow(() -> new UnknownEnumElementException(stateString));
        dateValidation(start, end);

        Map<String, Object> param = new HashMap<>();
        param.put("state", state);
        param.put("start", start);
        param.put("end", end);
        param.put("available", available);
        param.put("from", from);
        param.put("size", size);

        return client.getEventsWhereParticipant(followerId, userId, param);
    }

    @GetMapping("/{userId}/events/creator")
    public ResponseEntity<Object> getEventsWhereCreator(@RequestHeader("X-EWM-User-Id") long followerId,
                                                        @PathVariable(name = "userId") Long userId,
                                                        @RequestParam(name = "state") String stateString,
                                                        @RequestParam(name = "rangeStart", required = false) String start,
                                                        @RequestParam(name = "rangeEnd", required = false) String end,
                                                        @RequestParam(name = "available", required = false) Boolean available,
                                                        @RequestParam(name = "from", defaultValue = "0") int from,
                                                        @RequestParam(name = "size", defaultValue = "10") int size) {
        EventState state = EventState.from(stateString).orElseThrow(() -> new UnknownEnumElementException(stateString));
        dateValidation(start, end);

        Map<String, Object> param = new HashMap<>();
        param.put("state", state);
        param.put("start", start);
        param.put("end", end);
        param.put("available", available);
        param.put("from", from);
        param.put("size", size);

        return client.getEventsWhereCreator(followerId, userId, param);
    }

}
