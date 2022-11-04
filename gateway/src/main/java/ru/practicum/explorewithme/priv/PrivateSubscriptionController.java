package ru.practicum.explorewithme.priv;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.clients.server.priv.SubscriptionClient;
import ru.practicum.explorewithme.exceptions.UnknownEnumElementException;
import ru.practicum.explorewithme.models.subscription.NewSubscriptionRequest;
import ru.practicum.explorewithme.models.subscription.SubscriptionStatus;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PrivateSubscriptionController {

    private final SubscriptionClient client;

    @PostMapping("/{follower}/subscribe/{publisher}")
    public ResponseEntity<Object> addSubscribe(@PathVariable(name = "follower") Long follower,
                                            @PathVariable(name = "publisher") Long publisher,
                                            @RequestBody NewSubscriptionRequest request) {
        return client.addSubscribe(follower, publisher, request);
    }

    @PatchMapping("/{follower}/subscribe/{subscriptionId}/revoke")
    public ResponseEntity<Object> revokeRequestBySubscriber(@PathVariable(name = "follower") Long follower,
                                            @PathVariable(name = "subscriptionId") Long subscriptionId) {
        return client.revokeRequestBySubscriber(follower, subscriptionId);
    }
    @PatchMapping("/{publisher}/subscribe/{subscriptionId}/cancel")
    public ResponseEntity<Object> cancelRequestByPublisher(@PathVariable(name = "subscriptionId") Long subscriptionId,
                                            @PathVariable(name = "publisher") Long publisher) {
        return client.cancelRequestByPublisher(publisher, subscriptionId);
    }

    @PatchMapping("/{publisherId}/subscribe/{subscriptionId}/friendship")
    public ResponseEntity<Object> acceptSubscribe(@PathVariable(name = "subscriptionId") Long subscriptionId,
                                                  @PathVariable(name = "publisherId") Long publisherId,
                                                  @RequestParam(name = "friendship") Boolean friendship) {
        return client.acceptFriendship(publisherId, subscriptionId, friendship);
    }

    @GetMapping("/{userId}/subscribe/subscribed")
    public ResponseEntity<Object> getSubscribed(@PathVariable(name = "userId") long userId,
                                                @RequestParam(name = "status", required = false) String stringStatus,
                                                @RequestParam(name = "from", defaultValue = "0") int from,
                                                @RequestParam(name = "size", defaultValue = "10") int size) {
        SubscriptionStatus status = stringStatus == null ? null : SubscriptionStatus.from(stringStatus)
                .orElseThrow(() -> new UnknownEnumElementException(stringStatus));

        return client.getSubscribed(userId, status, from, size);
    }
    @GetMapping("/{userId}/subscribe/signed")
    public ResponseEntity<Object> getSigned(@PathVariable(name = "userId") long userId,
                                                @RequestParam(name = "status", required = false) String stringStatus,
                                                @RequestParam(name = "from", defaultValue = "0") int from,
                                                @RequestParam(name = "size", defaultValue = "10") int size) {
        SubscriptionStatus status = stringStatus == null ? null : SubscriptionStatus.from(stringStatus)
                .orElseThrow(() -> new UnknownEnumElementException(stringStatus));

        return client.getSigned(userId, status, from, size);
    }

    @GetMapping("/{userId}/subscriptions")
    public ResponseEntity<Object> getSubscriptions(@PathVariable(name = "userId") long userId,
                                                   @RequestParam(name = "friends") Boolean friends,
                                                @RequestParam(name = "from", defaultValue = "0") int from,
                                                @RequestParam(name = "size", defaultValue = "10") int size) {

        return client.getSubscriptions(userId, friends, from, size);
    }
    @GetMapping("/{userId}/followers")
    public ResponseEntity<Object> getFollowers(@PathVariable(name = "userId") long userId,
                                            @RequestParam(name = "friends") Boolean friends,
                                            @RequestParam(name = "from", defaultValue = "0") int from,
                                            @RequestParam(name = "size", defaultValue = "10") int size) {

        return client.getFollowers(userId, friends, from, size);
    }
}
