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

    @PatchMapping("/{publisher}/subscribe/{subscriptionId}/friendship")
    public void acceptSubscribe(@PathVariable(name = "subscriptionId") Long subscriptionId,
                                @PathVariable(name = "publisher") Long publisher,
                                @RequestParam(name = "friendship") Boolean friendship,
                                @RequestParam(name = "group", required = false) FriendshipGroup group) {

        service.acceptSubscribe(publisher, subscriptionId, friendship, group);
    }

    @GetMapping("/{userId}/subscribe/subscribed")
    public List<SubscriptionRequestDto> getIncomingSubscriptions(@PathVariable(name = "userId") long userId,
                                     @RequestParam(name = "status", required = false) SubscriptionStatus status,
                                     @RequestParam(name = "from") int from, @RequestParam(name = "size") int size) {
        return service.getIncomingSubscriptions(userId, status, from, size);
    }

    @GetMapping("/{userId}/subscribe/signed")
    public List<SubscriptionRequestDto> getOutgoingSubscriptions(@PathVariable(name = "userId") long userId,
                                                                 @RequestParam(name = "status", required = false) SubscriptionStatus status,
                                                                 @RequestParam(name = "from") int from, @RequestParam(name = "size") int size) {
        return service.getOutgoingSubscriptions(userId, status, from, size);
    }

    @GetMapping("/{userId}/subscriptions")
    public List<UserShortDto> getSubscriptions(@PathVariable(name = "userId") long userId,
                                               @RequestParam(name = "friends") boolean friends,
                                               @RequestParam(name = "from", defaultValue = "0") int from,
                                               @RequestParam(name = "size", defaultValue = "10") int size) {

        return service.getSubscriptions(userId, friends, from, size);
    }
    @GetMapping("/{userId}/followers")
    public List<UserShortDto> getFollowers(@PathVariable(name = "userId") long userId,
                                               @RequestParam(name = "friends") boolean friends,
                                               @RequestParam(name = "from", defaultValue = "0") int from,
                                               @RequestParam(name = "size", defaultValue = "10") int size) {

        return service.getFollowers(userId, friends, from, size);
    }
}
