package ru.practicum.explorewithme.server.controllers.priv;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.models.request.ParticipationRequestDto;
import ru.practicum.explorewithme.server.services.priv.RequestService;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor(onConstructor_ = @Autowired)
public class RequestController {

    private final RequestService service;

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
