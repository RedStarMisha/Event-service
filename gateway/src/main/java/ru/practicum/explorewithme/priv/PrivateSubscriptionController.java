package ru.practicum.explorewithme.priv;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.clients.server.priv.SubscriptionClient;
import ru.practicum.explorewithme.models.subscription.NewSubscriptionRequest;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PrivateSubscriptionController {

    private final SubscriptionClient client;

    @PostMapping("/{subscriberId}/subscribe/{publisher}")
    public ResponseEntity<Object> addSubscribe(@PathVariable(name = "subscriberId") Long subscriberId,
                                            @PathVariable(name = "publisher") Long publisher,
                                            @RequestBody NewSubscriptionRequest request) {
        return client.addSubcribe(subscriberId, publisher, request);
    }

    @PatchMapping("/{subscriberId}/subscribe/{publisher}/revoke")
    public ResponseEntity<Object> revokeRequestBySubscriber(@PathVariable(name = "subscriberId") Long subscriberId,
                                            @PathVariable(name = "publisher") Long publisher) {
        return client.revokeRequestBySubscriber(subscriberId, publisher);
    }
    @PatchMapping("/{publisher}/subscribe/{subscriberId}/cancel")
    public ResponseEntity<Object> cancelRequestByPublisher(@PathVariable(name = "subscriberId") Long subscriberId,
                                            @PathVariable(name = "publisher") Long publisher) {
        return client.cancelRequestByPublisher(subscriberId, publisher);
    }

    @PatchMapping("/{publisher}/subscribe/{subscriberId}")
    public ResponseEntity<Object> acceptSubscribe(@PathVariable(name = "subscriberId") Long subscriberId,
                                                  @PathVariable(name = "publisher") Long publisher,
                                                  @RequestParam(name = "friendship") Boolean friendship) {
        return client.acceptSubscribe(subscriberId, publisher, friendship);
    }
}
