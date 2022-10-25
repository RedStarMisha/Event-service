package ru.practicum.explorewithme.server.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.models.request.ParticipationRequestDto;
import ru.practicum.explorewithme.models.event.EventFullDto;
import ru.practicum.explorewithme.models.event.EventShortDto;
import ru.practicum.explorewithme.models.event.NewEventDto;
import ru.practicum.explorewithme.models.event.UpdateEventRequest;
import ru.practicum.explorewithme.server.services.PrivateService;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor(onConstructor_ = @Autowired)
public class PrivateController {

    private final PrivateService service;


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

    @GetMapping("/{userId}/requests")
    public List<ParticipationRequestDto> getEventRequestsByUser(@PathVariable(name = "userId") long userId) {
        return service.getEventRequestsByUser(userId);
    }

    @PostMapping("/{userId}/requests")
    public ParticipationRequestDto addNewRequestByUser(@PathVariable(name = "userId") long userId,
                                                      @RequestParam(name = "eventId") long eventId) {
        return service.addNewRequestByUser(userId, eventId);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelUserRequest(@PathVariable(name = "userId") long userId,
                                                    @PathVariable(name = "requestId") long requestId) {
        return service.cancelUserRequest(userId, requestId);
    }

}
