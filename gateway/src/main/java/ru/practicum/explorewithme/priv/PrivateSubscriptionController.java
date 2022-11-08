package ru.practicum.explorewithme.priv;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.clients.server.priv.SubscriptionClient;
import ru.practicum.explorewithme.exceptions.UnknownEnumElementException;
import ru.practicum.explorewithme.models.subscription.FriendshipGroup;
import ru.practicum.explorewithme.models.subscription.NewGroupDto;
import ru.practicum.explorewithme.models.subscription.NewSubscriptionRequest;
import ru.practicum.explorewithme.models.subscription.SubscriptionStatus;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PrivateSubscriptionController {

    private final SubscriptionClient client;

    @PostMapping("/{publisherId}/subscribe")
    public ResponseEntity<Object> addSubscribe(@RequestHeader("X-EWM-User-Id") Long userId,
                                            @PathVariable(name = "publisherId") Long publisherId,
                                            @RequestBody NewSubscriptionRequest request) {
        return client.addSubscribe(userId, publisherId, request);
    }

    @GetMapping("/subscriptions/{subscriptionId}")
    public ResponseEntity<Object> getSubscription(@RequestHeader("X-EWM-User-Id") Long userId,
                                                            @PathVariable(name = "subscriptionId") Long subscriptionId) {
        return client.getSubscription(userId, subscriptionId);
    }

    @PatchMapping("/subscriptions/{subscriptionId}/revoke")
    public ResponseEntity<Object> revokeRequestBySubscriber(@RequestHeader("X-EWM-User-Id") Long userId,
                                                            @PathVariable(name = "subscriptionId") Long subscriptionId) {
        return client.revokeRequestBySubscriber(userId, subscriptionId);
    }
    @PatchMapping("/subscriptions/{subscriptionId}/cancel")
    public ResponseEntity<Object> cancelRequestByPublisher(@RequestHeader("X-EWM-User-Id") Long userId,
                                                           @PathVariable(name = "subscriptionId") Long subscriptionId) {
        return client.cancelRequestByPublisher(userId, subscriptionId);
    }

    @PatchMapping("/subscriptions/{subscriptionId}/accept")
    public ResponseEntity<Object> acceptSubscribe(@RequestHeader("X-EWM-User-Id") Long userId,
                                                  @PathVariable(name = "subscriptionId") Long subscriptionId,
                                                  @RequestParam(name = "friendship") Boolean friendship,
                                                  @RequestParam(name = "group", required = false) FriendshipGroup group) {
        return client.acceptSubscribe(userId, subscriptionId, friendship, group);
    }

    @GetMapping("/subscriptions/incoming")
    public ResponseEntity<Object> getIncomingSubscriptions(@RequestHeader("X-EWM-User-Id") Long userId,
                                                @RequestParam(name = "status", required = false) String stringStatus,
                                                @RequestParam(name = "from", defaultValue = "0") int from,
                                                @RequestParam(name = "size", defaultValue = "10") int size) {
        SubscriptionStatus status = stringStatus == null ? null : SubscriptionStatus.from(stringStatus)
                .orElseThrow(() -> new UnknownEnumElementException(stringStatus));

        return client.getIncomingSubscriptions(userId, status, from, size);
    }
    @GetMapping("/subscriptions/outgoing")
    public ResponseEntity<Object> getOutgoingSubscriptions(@RequestHeader("X-EWM-User-Id") Long userId,
                                                @RequestParam(name = "status", required = false) String stringStatus,
                                                @RequestParam(name = "from", defaultValue = "0") int from,
                                                @RequestParam(name = "size", defaultValue = "10") int size) {
        SubscriptionStatus status = stringStatus == null ? null : SubscriptionStatus.from(stringStatus)
                .orElseThrow(() -> new UnknownEnumElementException(stringStatus));

        if (status == SubscriptionStatus.REVOKE) throw new UnknownEnumElementException(stringStatus);

        return client.getOutgoingSubscriptions(userId, status, from, size);
    }

    @PostMapping("/groups")
    public ResponseEntity<Object> addNewGroup(@RequestHeader("X-EWM-User-Id") Long userId,
                            @RequestBody NewGroupDto groupDto) {
        return client.addNewGroup(userId, groupDto);
    }
}
