package ru.practicum.explorewithme.clients.server.priv;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RestTemplate;
import ru.practicum.explorewithme.clients.BaseClient;
import ru.practicum.explorewithme.models.subscription.FriendshipGroup;
import ru.practicum.explorewithme.models.subscription.NewSubscriptionRequest;
import ru.practicum.explorewithme.models.subscription.SubscriptionStatus;

import java.util.HashMap;
import java.util.Map;

public class SubscriptionClient extends BaseClient {

    public SubscriptionClient(RestTemplate rest) {
        super(rest);
    }

    public ResponseEntity<Object> addSubscribe(Long userId, Long publisherId, NewSubscriptionRequest request) {
        return post("/" + publisherId + "/subscribe", userId, request);
    }

    public ResponseEntity<Object> revokeRequestBySubscriber(Long userId, Long subscriptionId) {
        return patch("/subscriptions/" + subscriptionId + "/revoke", userId);
    }

    public ResponseEntity<Object> cancelRequestByPublisher(Long userId, Long subscriptionId) {
        return patch("/subscriptions/" + subscriptionId + "/cancel", userId);
    }

    public ResponseEntity<Object> acceptSubscribe(Long userId, Long subscriptionId, Boolean friendship,
                                                  FriendshipGroup group) {
        Map<String, Object> param = new HashMap<>();
        param.put("friendship", friendship);
        param.put("group", group);

        String queryPath = "?friendship={friendship}&group={group}";

        return patch("/subscriptions/" + subscriptionId + "/accept" + queryPath, userId, param);
    }

    public ResponseEntity<Object> getIncomingSubscriptions(Long userId, SubscriptionStatus status, int from, int size) {
        Map<String, Object> param = new HashMap<>();
        param.put("status", status);
        param.put("from", from);
        param.put("size", size);

        String queryPath = "?status={status}&from={from}&size={size}";

        return get("/subscriptions/incoming" + queryPath, userId, param);
    }

    public ResponseEntity<Object> getOutgoingSubscriptions(long userId, SubscriptionStatus status, int from, int size) {
        Map<String, Object> param = new HashMap<>();
        param.put("status", status);
        param.put("from", from);
        param.put("size", size);

        String queryPath = "?status={status}&from={from}&size={size}";

        return get("/subscriptions/outgoing" + queryPath, userId, param);
    }

    public ResponseEntity<Object> getFollowing(long followerId, long userId, Boolean friends, int from, int size) {
        Map<String, Object> param = Map.of(
                "friends", friends,
                "from", from,
                "size", size
        );

        String queryPath = "?friends={friends}&from={from}&size={size}";

        return get("/" + userId + "/following" + queryPath, followerId, param);
    }

    public ResponseEntity<Object> getFollowers(long followerId, long userId, Boolean friends,
                                               @Nullable FriendshipGroup friendshipGroup, int from, int size) {
        Map<String, Object> param = Map.of(
                "friends", friends,
                "friendshipGroup", friendshipGroup,
                "from", from,
                "size", size
        );

        String queryPath = "?friends={friends}&friendshipGroup={friendshipGroup}&from={from}&size={size}";

        return get("/" + userId + "/followers" + queryPath, followerId, param);
    }

    public ResponseEntity<Object> getUser(long followerId, long userId) {
        return get("/" + userId, followerId);
    }
}
