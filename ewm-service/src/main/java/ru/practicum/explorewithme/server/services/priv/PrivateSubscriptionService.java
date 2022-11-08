package ru.practicum.explorewithme.server.services.priv;

import ru.practicum.explorewithme.models.subscription.*;

import java.util.List;

public interface PrivateSubscriptionService {
    SubscriptionRequestDto addSubscribe(Long followerId, Long publisherId, NewSubscriptionRequest newRequest);

    SubscriptionRequestDto revokeRequestBySubscriber(Long followerId, Long subscriptionId);

    SubscriptionRequestDto cancelSubscription(Long userId, Long subscriptionId, Boolean fully);

    SubscriptionRequestDto acceptSubscribe(long publisherId, long friendshipId, boolean friendship, FriendshipGroup group);

    List<SubscriptionRequestDto> getIncomingSubscriptions(long followerId, SubscriptionStatus status, int from, int size);

    List<SubscriptionRequestDto> getOutgoingSubscriptions(long userId, SubscriptionStatus status, int from, int size);

    void addNewGroup(Long userId, NewGroupDto groupDto);

    SubscriptionRequestDto getSubscription(Long userId, Long subscriptionId);
}
