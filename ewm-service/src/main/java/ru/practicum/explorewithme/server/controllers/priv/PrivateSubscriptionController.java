package ru.practicum.explorewithme.server.controllers.priv;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.models.subscription.*;
import ru.practicum.explorewithme.server.models.Group;
import ru.practicum.explorewithme.server.services.priv.PrivateSubscriptionService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PrivateSubscriptionController {

    private final PrivateSubscriptionService service;

    @PostMapping("/{publisherId}/subscribe")
    public SubscriptionRequestDto addSubscribe(@RequestHeader("X-EWM-User-Id") long userId,
                                               @PathVariable(name = "publisherId") Long publisherId,
                                               @RequestBody NewSubscriptionRequest request) {
        return service.addSubscribe(userId, publisherId, request);
    }

    @GetMapping("/subscriptions/{subscriptionId}")
    public SubscriptionRequestDto getSubscription(@RequestHeader("X-EWM-User-Id") Long userId,
                                                  @PathVariable(name = "subscriptionId") Long subscriptionId) {
        return service.getSubscription(userId, subscriptionId);
    }

    @PatchMapping("/subscriptions/{subscriptionId}/revoke")
    public void revokeRequestBySubscriber(@RequestHeader("X-EWM-User-Id") Long userId,
                                          @PathVariable(name = "subscriptionId") Long subscriptionId) {
        service.revokeRequestBySubscriber(userId, subscriptionId);
    }

    @PatchMapping("/subscriptions/{subscriptionId}/cancel")
    public void cancelRequestByPublisher(@RequestHeader("X-EWM-User-Id") Long userId,
                                         @PathVariable(name = "subscriptionId") Long subscriptionId) {
        service.cancelRequestByPublisher(userId, subscriptionId);
    }

    @PatchMapping("/subscriptions/{subscriptionId}/accept")
    public void acceptSubscribe(@RequestHeader("X-EWM-User-Id") Long userId,
                                @PathVariable(name = "subscriptionId") Long subscriptionId,
                                @RequestParam(name = "friendship") Boolean friendship,
                                @RequestParam(name = "group", required = false) FriendshipGroup group) {

        service.acceptSubscribe(userId, subscriptionId, friendship, group);
    }

    @GetMapping("/subscriptions/incoming")
    public List<SubscriptionRequestDto> getIncomingSubscriptions(@RequestHeader("X-EWM-User-Id") Long userId,
                                     @RequestParam(name = "status", required = false) SubscriptionStatus status,
                                     @RequestParam(name = "from") int from, @RequestParam(name = "size") int size) {
        return service.getIncomingSubscriptions(userId, status, from, size);
    }

    @GetMapping("/subscriptions/outgoing")
    public List<SubscriptionRequestDto> getOutgoingSubscriptions(@RequestHeader("X-EWM-User-Id") Long userId,
                                             @RequestParam(name = "status", required = false) SubscriptionStatus status,
                                             @RequestParam(name = "from") int from, @RequestParam(name = "size") int size) {
        return service.getOutgoingSubscriptions(userId, status, from, size);
    }

    @PostMapping("/groups")
    public void addNewGroup(@RequestHeader("X-EWM-User-Id") Long userId,
                             @RequestBody NewGroupDto groupDto) {
        service.addNewGroup(userId, groupDto);
    }
}
