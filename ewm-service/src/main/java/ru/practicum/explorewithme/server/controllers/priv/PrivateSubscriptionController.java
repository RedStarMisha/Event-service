package ru.practicum.explorewithme.server.controllers.priv;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.models.subscription.NewSubscriptionRequest;
import ru.practicum.explorewithme.models.subscription.SubscriptionRequestDto;
import ru.practicum.explorewithme.models.subscription.SubscriptionStatus;
import ru.practicum.explorewithme.server.services.priv.PrivateSubscriptionService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PrivateSubscriptionController {

    private final PrivateSubscriptionService service;

    @PostMapping("/{followerId}/subscribe/{publisher}")
    public SubscriptionRequestDto addSubscribe(@PathVariable(name = "followerId") Long followerId,
                                               @PathVariable(name = "publisher") Long publisher,
                                               @RequestBody NewSubscriptionRequest request) {
        SubscriptionRequestDto dto = service.addSubscribe(followerId, publisher, request);
        return dto;
    }

    @PatchMapping("/{followerId}/subscribe/{publisher}/revoke")
    public void revokeRequestBySubscriber(@PathVariable(name = "followerId") Long followerId,
                                                            @PathVariable(name = "publisher") Long publisher) {
        service.revokeRequestBySubscriber(followerId, publisher);
    }

    @PatchMapping("/{publisher}/subscribe/{followerId}/cancel")
    public void cancelRequestByPublisher(@PathVariable(name = "followerId") Long followerId,
                                                           @PathVariable(name = "publisher") Long publisher) {
        service.cancelRequestByPublisher(followerId, publisher);
    }

    @PatchMapping("/{publisher}/friendship/{friendshipId}/")
    public void acceptFriendshipByPublisher(@PathVariable(name = "friendshipId") Long friendshipId,
                                            @PathVariable(name = "publisher") Long publisher) {
        service.acceptFriendship(publisher, friendshipId);
    }

    @GetMapping("/{followerId}/subscribe")
    public List<SubscriptionRequestDto> getSubscriptions(@PathVariable(name = "followerId") long followerId,
                                             @RequestParam(name = "status", required = false) SubscriptionStatus status,
                                             @RequestParam(name = "from") int from, @RequestParam(name = "size") int size) {
        return service.getSubscriptions(followerId, status, from, size);
    }
}
