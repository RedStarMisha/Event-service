package ru.practicum.explorewithme.server.controllers.priv;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.models.event.*;
import ru.practicum.explorewithme.models.request.ParticipationRequestDto;
import ru.practicum.explorewithme.server.services.priv.PrivateEventService;
import ru.practicum.explorewithme.server.utils.selectioncondition.SelectionConditionForPrivate;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class PrivateEventController {

    private final PrivateEventService service;

    @GetMapping("/{userId}/events")
    public List<EventShortDto> getEventsByOwnerId(@PathVariable(name = "userId") long userId,
                                                  @RequestParam(name = "from", defaultValue = "0") int from,
                                                  @RequestParam(name = "size", defaultValue = "10") int size) {
        return service.getEventsByOwnerId(userId, from, size);
    }

    @PatchMapping("/{userId}/events")
    public EventFullDto updateEvent(@PathVariable(name = "userId") long userId,
                                    @RequestBody UpdateEventRequest request) {
        return service.updateEvent(userId, request);
    }

    @PostMapping("/{userId}/events")
    public EventFullDto addEvent(@PathVariable(name = "userId") Long userId,
                                 @RequestBody NewEventDto newEventDto) {
        return service.addEvent(userId, newEventDto);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto getEventByOwnerIdAndEventId(@PathVariable(name = "userId") long userId,
                                                    @PathVariable(name = "eventId") long eventId) {
        return service.getEventByOwnerIdAndEventId(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public EventFullDto cancelEventByOwner(@PathVariable(name = "userId") long userId,
                                           @PathVariable(name = "eventId") long eventId) {
        return service.cancelEventByOwner(userId, eventId);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getEventRequests(@PathVariable(name = "userId") long userId,
                                                          @PathVariable(name = "eventId") long eventId) {
        return service.getEventRequests(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmRequestForEvent(@PathVariable(name = "userId") long userId,
                                                          @PathVariable(name = "eventId") long eventId,
                                                          @PathVariable(name = "reqId") long reqId) {
        return service.confirmRequestForEvent(userId, eventId, reqId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectRequestForEvent(@PathVariable(name = "userId") long userId,
                                                         @PathVariable(name = "eventId") long eventId,
                                                         @PathVariable(name = "reqId") long reqId) {
        return service.rejectRequestForEvent(userId, eventId, reqId);
    }

    @GetMapping("/{userId}/events/participant")
    public List<EventFullDto> getEventsWhereParticipant(@RequestHeader("X-EWM-User-Id") long followerId,
                                                        @PathVariable(name = "userId") Long userId,
                                                        @RequestParam(name = "state") EventState state,
                                                        @RequestParam(name = "rangeStart", required = false) String start,
                                                        @RequestParam(name = "rangeEnd", required = false) String end,
                                                        @RequestParam(name = "available", required = false) Boolean available,
                                                        @RequestParam(name = "from", defaultValue = "0") int from,
                                                        @RequestParam(name = "size", defaultValue = "10") int size) {
        SelectionConditionForPrivate selection = SelectionConditionForPrivate.of(userId, state, start, end, available,
                from, size);
        return service.getEventsWhereParticipant(followerId, userId, selection);
    }

    @GetMapping("/{userId}/events/creator")
    public List<EventFullDto> getEventsWhereCreator(@RequestHeader("X-EWM-User-Id") long followerId,
                                                    @PathVariable(name = "userId") Long userId,
                                                    @RequestParam(name = "state") EventState state,
                                                    @RequestParam(name = "rangeStart", required = false) String start,
                                                    @RequestParam(name = "rangeEnd", required = false) String end,
                                                    @RequestParam(name = "available", required = false) Boolean available,
                                                    @RequestParam(name = "from", defaultValue = "0") int from,
                                                    @RequestParam(name = "size", defaultValue = "10") int size) {
        SelectionConditionForPrivate selection = SelectionConditionForPrivate.of(userId, state, start, end, available,
                from, size);

        return service.getEventsWhereCreator(followerId, userId, selection);
    }
}
