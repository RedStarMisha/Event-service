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

    @PatchMapping("/{follower}/subscribe/{publisher}/revoke")
    public ResponseEntity<Object> revokeRequestBySubscriber(@PathVariable(name = "follower") Long follower,
                                            @PathVariable(name = "publisher") Long publisher) {
        return client.revokeRequestBySubscriber(follower, publisher);
    }
    @PatchMapping("/{publisher}/subscribe/{follower}/cancel")
    public ResponseEntity<Object> cancelRequestByPublisher(@PathVariable(name = "follower") Long follower,
                                            @PathVariable(name = "publisher") Long publisher) {
        return client.cancelRequestByPublisher(follower, publisher);
    }

    @PatchMapping("/{publisher}/friendship/{friendshipId}/")
    public ResponseEntity<Object> acceptSubscribe(@PathVariable(name = "friendshipId") Long friendshipId,
                                                  @PathVariable(name = "publisher") Long publisher) {
        return client.acceptFriendship(publisher, friendshipId);
    }

    @GetMapping("/{follower}/subscribe")
    public ResponseEntity<Object> getSubscribes(@PathVariable(name = "follower") long follower,
                                                @RequestParam(name = "status", required = false) String stringStatus,
                                                @RequestParam(name = "from", defaultValue = "0") int from,
                                                @RequestParam(name = "size", defaultValue = "10") int size) {
        SubscriptionStatus status = stringStatus == null ? null : SubscriptionStatus.from(stringStatus)
                .orElseThrow(() -> new UnknownEnumElementException(stringStatus));
        return client.getSubscribes(follower, status, from, size);
    }
}
