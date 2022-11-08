package ru.practicum.explorewithme.server.controllers.priv;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.models.subscription.FriendshipGroup;
import ru.practicum.explorewithme.models.subscription.NewSubscriptionRequest;
import ru.practicum.explorewithme.models.subscription.SubscriptionRequestDto;
import ru.practicum.explorewithme.models.subscription.SubscriptionStatus;
import ru.practicum.explorewithme.models.user.UserShortDto;
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

    @PatchMapping("/subscriptions/{subscriptionId}/revoke")
    public void revokeRequestBySubscriber(@RequestHeader("X-EWM-User-Id") Long userId,
                                          @PathVariable(name = "subscriptionId") Long subscriptionId) {
        service.revokeRequestBySubscriber(userId, subscriptionId);
    }

    @PatchMapping("/{publisher}/subscribe/{subscriptionId}/cancel")
    public void cancelRequestByPublisher(@PathVariable(name = "subscriptionId") Long subscriptionId,
                                                           @PathVariable(name = "publisher") Long publisher) {
        service.cancelRequestByPublisher(publisher, subscriptionId);
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
}
