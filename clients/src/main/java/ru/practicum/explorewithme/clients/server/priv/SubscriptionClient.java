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

    public ResponseEntity<Object> revokeRequestBySubscriber(Long follower, Long subscriptionId) {
        return null;
    }

    public ResponseEntity<Object> cancelRequestByPublisher(Long publisher, Long subscriptionId) {
        return null;
    }

    public ResponseEntity<Object> acceptFriendship(Long publisher, Long subscriptionId, Boolean friendship) {
        Map<String, Object> param = Map.of("friendship", friendship);
        return patch("/" + publisher + "/subscribe/" + subscriptionId + "/friendship", param);
    }

    public ResponseEntity<Object> getSubscribed(long followerId, SubscriptionStatus status, int from, int size) {
        Map<String, Object> param = new HashMap<>();
        param.put("status", status);
        param.put("from", from);
        param.put("size", size);

        String queryPath = "?status={status}&from={from}&size={size}";

        return get("/" + followerId + "/subscribe/subscribed" + queryPath, param);
    }

    public ResponseEntity<Object> getSigned(long publisherId, SubscriptionStatus status, int from, int size) {
        Map<String, Object> param = new HashMap<>();
        param.put("status", status);
        param.put("from", from);
        param.put("size", size);

        String queryPath = "?status={status}&from={from}&size={size}";

        return get("/" + publisherId + "/subscribe/signed" + queryPath, param);
    }

    public ResponseEntity<Object> getSubscriptions(long userId, Boolean friends, int from, int size) {
        Map<String, Object> param = Map.of(
                "friends", friends,
                "from", from,
                "size", size
        );

        String queryPath = "?friends={friends}&from={from}&size={size}";

        return get("/" + userId + "/subscriptions" + queryPath, param);
    }

    public ResponseEntity<Object> getFollowers(long userId, Boolean friends, int from, int size) {
        Map<String, Object> param = Map.of(
                "friends", friends,
                "from", from,
                "size", size
        );

        String queryPath = "?friends={friends}&from={from}&size={size}";

        return get("/" + userId + "/followers" + queryPath, param);
    }
}
