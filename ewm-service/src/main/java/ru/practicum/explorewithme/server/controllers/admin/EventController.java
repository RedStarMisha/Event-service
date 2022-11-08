package ru.practicum.explorewithme.server.controllers.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.models.event.AdminUpdateEventRequest;
import ru.practicum.explorewithme.models.event.EventFullDto;
import ru.practicum.explorewithme.models.event.State;
import ru.practicum.explorewithme.server.services.admin.EventService;
import ru.practicum.explorewithme.server.utils.selectioncondition.SelectionConditionForAdmin;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EventController {

    private final EventService service;

    @GetMapping
    public List<EventFullDto> getEvents(@RequestParam(name = "users", required = false) int[] users,
                                        @RequestParam(name = "states", required = false) State[] states,
                                        @RequestParam(name = "categories", required = false) int[] categories,
                                        @RequestParam(name = "rangeStart", required = false) String rangeStart,
                                        @RequestParam(name = "rangeEnd", required = false) String rangeEnd,
                                        @RequestParam(name = "from", defaultValue = "0") int from,
                                        @RequestParam(name = "size", defaultValue = "10") int size, HttpServletRequest request) {

        SelectionConditionForAdmin condition = SelectionConditionForAdmin.of(users, states, categories, rangeStart,
                rangeEnd, from, size);

        return service.getEvents(condition, request);
    }

    @PutMapping("/{eventId}")
    public EventFullDto updateEvent(@PathVariable(name = "eventId") long eventId,
                                              @RequestBody AdminUpdateEventRequest updateEventRequest) {
        return service.updateEvent(eventId, updateEventRequest);
    }

    @PatchMapping("/{eventId}/publish")
    public EventFullDto publishEvent(@PathVariable(name = "eventId") long eventId) {
        return service.publishEvent(eventId);
    }

    @PatchMapping("/{eventId}/reject")
    public EventFullDto rejectEvent(@PathVariable(name = "eventId") long eventId) {
        return service.rejectEvent(eventId);
    }
}
