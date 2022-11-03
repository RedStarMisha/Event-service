package ru.practicum.explorewithme.clients.server.priv;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.explorewithme.clients.BaseClient;
import ru.practicum.explorewithme.models.subscription.NewSubscriptionRequest;
import ru.practicum.explorewithme.models.subscription.SubscriptionStatus;

import java.util.HashMap;
import java.util.Map;

public class SubscriptionClient extends BaseClient {
    
    public SubscriptionClient(RestTemplate rest) {
        super(rest);
    }

    public ResponseEntity<Object> addSubscribe(Long follower, Long publisher, NewSubscriptionRequest request) {
        return post("/" + follower + "/subscribe/" + publisher, request);
    }

    public ResponseEntity<Object> revokeRequestBySubscriber(Long follower, Long publisher) {
        return null;
    }

    public ResponseEntity<Object> cancelRequestByPublisher(Long follower, Long publisher) {
        return null;
    }

    public ResponseEntity<Object> acceptFriendship(Long publisher, Long friendshipId) {
        return patch("/" + publisher + "/friendship/" + friendshipId);
    }

    public ResponseEntity<Object> getSubscribes(long follower, SubscriptionStatus status, int from, int size) {
        Map<String, Object> param = new HashMap<>();
        param.put("status", status);
        param.put("from", from);
        param.put("size", size);
        String queryPath = "?status={status}&from={from}&size={size}";
        return get("/" + follower + "/subscribe" + queryPath, param);
    }
}
