package ru.practicum.explorewithme.admin;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.clients.server.admin.EventClient;
import ru.practicum.explorewithme.models.event.AdminUpdateEventRequest;
import ru.practicum.explorewithme.models.event.State;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.HashMap;
import java.util.Map;

import static ru.practicum.explorewithme.validation.ValidUtil.dateValidation;

@RestController
@RequestMapping("/admin/events")
@AllArgsConstructor(onConstructor_ = @Autowired)
@Validated
public class EventController {

    private final EventClient client;

    @GetMapping
    public ResponseEntity<Object> getEvents(@RequestParam(name = "users", required = false) int[] users,
                                            @RequestParam(name = "states", required = false) State[] states,
                                            @RequestParam(name = "categories", required = false) int[] categories,
                                            @RequestParam(name = "rangeStart", required = false) String rangeStart,
                                            @RequestParam(name = "rangeEnd", required = false) String rangeEnd,
                                            @RequestParam(name = "from", defaultValue = "0") Integer from,
                                            @RequestParam(name = "size", defaultValue = "10") Integer size) {
        dateValidation(rangeStart, rangeEnd);

        Map<String, Object> param = new HashMap<>();
        param.put("users", users);
        param.put("states", states);
        param.put("categories", categories);
        param.put("rangeStart", rangeStart);
        param.put("rangeEnd", rangeEnd);
        param.put("from", from);
        param.put("size", size);

        return client.getEvents(param);
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<Object> updateEvent(@PathVariable(name = "eventId") long eventId,
                                              @RequestBody AdminUpdateEventRequest updateEventRequest) {
        return client.updateEvent(eventId, updateEventRequest);
    }

    @PatchMapping("/{eventId}/publish")
    public ResponseEntity<Object> publishEvent(@PathVariable(name = "eventId") long eventId) {
        return client.publishEvent(eventId);
    }

    @PatchMapping("/{eventId}/reject")
    public ResponseEntity<Object> rejectEvent(@PathVariable(name = "eventId") long eventId) {
        return client.rejectEvent(eventId);
    }
}
