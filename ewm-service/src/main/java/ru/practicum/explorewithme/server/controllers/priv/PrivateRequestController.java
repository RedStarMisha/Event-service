package ru.practicum.explorewithme.server.controllers.priv;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.models.request.ParticipationRequestDto;
import ru.practicum.explorewithme.server.services.priv.PrivateRequestService;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PrivateRequestController {

    private final PrivateRequestService service;

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

    @PatchMapping("/{userId}/requests/{requestId}")
    public ParticipationRequestDto addGroupToRequest(@PathVariable(name = "userId") @Positive Long userId,
                                                     @PathVariable(name = "requestId") @Positive Long requestId,
                                                     @RequestParam(name = "group") Long group) {
        return service.addGroupToRequest(userId, requestId, group);
    }
}
