package ru.practicum.explorewithme.server.services.priv;

import ru.practicum.explorewithme.models.subscription.FriendshipGroup;
import ru.practicum.explorewithme.models.subscription.NewSubscriptionRequest;
import ru.practicum.explorewithme.models.subscription.SubscriptionRequestDto;
import ru.practicum.explorewithme.models.subscription.SubscriptionStatus;
import ru.practicum.explorewithme.models.user.UserShortDto;

import java.util.List;

public interface PrivateSubscriptionService {
    SubscriptionRequestDto addSubscribe(Long followerId, Long publisherId, NewSubscriptionRequest newRequest);

    void revokeRequestBySubscriber(Long followerId, Long subscriptionId);

    void cancelRequestByPublisher(Long publisherId, Long subscriptionId);

    void acceptSubscribe(long publisherId, long friendshipId, boolean friendship, FriendshipGroup group);

    List<SubscriptionRequestDto> getIncomingSubscriptions(long followerId, SubscriptionStatus status, int from, int size);

    List<SubscriptionRequestDto> getOutgoingSubscriptions(long userId, SubscriptionStatus status, int from, int size);

    List<UserShortDto> getSubscriptions(long userId, boolean friends, int from, int size);

    List<UserShortDto> getFollowers(long userId, boolean friends, int from, int size);
}
