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
    public void revokeRequestBySubscriber(@PathVariable(name = "subscriberId") Long subscriberId,
                                            @PathVariable(name = "publisher") Long publisher) {
        client.revokeRequestBySubscriber(subscriberId, publisher);
    }
}
