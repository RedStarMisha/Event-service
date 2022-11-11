package ru.practicum.explorewithme.clients.server.priv;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.explorewithme.clients.BaseClient;
import ru.practicum.explorewithme.models.subscription.NewSubscriptionRequest;
import ru.practicum.explorewithme.models.subscription.SubscriptionStatus;
import ru.practicum.explorewithme.models.subscription.UpdateFollowerDto;
import ru.practicum.explorewithme.models.subscription.group.FriendshipGroup;
import ru.practicum.explorewithme.models.subscription.group.NewGroupDto;

import java.util.HashMap;
import java.util.Map;

public class SubscriptionClient extends BaseClient {

    public SubscriptionClient(RestTemplate rest) {
        super(rest);
    }

    public ResponseEntity<Object> addSubscribe(Long userId, Long publisherId, NewSubscriptionRequest request) {
        return post("/subscriptions/" + publisherId + "/subscribe", userId, request);
    }

    public ResponseEntity<Object> cancelSubscription(Long userId, Long subscriptionId) {
        return patch("/subscriptions/" + subscriptionId + "/cancel", userId);
    }

    public ResponseEntity<Object> acceptSubscribe(Long userId, Long subscriptionId, Boolean friendship) {
        Map<String, Object> param = Map.of("friendship", friendship);

        String queryPath = "?friendship={friendship}";

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

    public ResponseEntity<Object> getFollowers(long followerId, long userId, Boolean friends, int from, int size) {
        Map<String, Object> param = Map.of(
                "friends", friends,
                "from", from,
                "size", size
        );

        String queryPath = "?friends={friends}&from={from}&size={size}";

        return get("/" + userId + "/followers" + queryPath, followerId, param);
    }

    public ResponseEntity<Object> getUser(long followerId, long userId) {
        return get("/" + userId, followerId);
    }

    public ResponseEntity<Object> addNewGroup(Long userId, NewGroupDto groupDto) {
        return post("/groups", userId, groupDto);
    }

    public ResponseEntity<Object> getSubscription(Long userId, Long subscriptionId) {
        return get("/subscriptions/" + subscriptionId, userId);
    }

    public ResponseEntity<Object> getGroups(Long userId) {
        return get("/groups", userId);
    }

    public ResponseEntity<Object> updateFollower(long publisherId, long followerId, UpdateFollowerDto follower) {
        return patch("/followers/" + followerId, publisherId, follower);
    }

    public ResponseEntity<Object> getOwnFollowing(long userId, boolean friends, int from, int size) {
        Map<String, Object> param = Map.of(
                "friends", friends,
                "from", from,
                "size", size
        );

        String queryPath = "?friends={friends}&from={from}&size={size}";
        return get("/following" + queryPath, userId, param);
    }

    public ResponseEntity<Object> getOwnFollowers(long userId, boolean friends, Long groupId, int from, int size) {
        Map<String, Object> param = new HashMap<>();
        param.put("friends", friends);
        param.put("groupId", groupId);
        param.put("from", from);
        param.put("size", size);

        String queryPath = "?friends={friends}&groupId={groupId}&from={from}&size={size}";
        return get("/followers" + queryPath, userId, param);
    }
}
