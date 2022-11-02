package ru.practicum.explorewithme.clients.server.priv;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.explorewithme.clients.BaseClient;
import ru.practicum.explorewithme.models.subscription.NewSubscriptionRequest;

public class SubscriptionClient extends BaseClient {
    
    public SubscriptionClient(RestTemplate rest) {
        super(rest);
    }

    public ResponseEntity<Object> addSubcribe(Long subscriberId, Long publisher, NewSubscriptionRequest request) {
    }

    public void revokeRequestBySubscriber(Long subscriberId, Long publisher) {
    }
}
