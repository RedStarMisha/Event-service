package ru.practicum.explorewithme.server.services.priv;

import ru.practicum.explorewithme.models.subscription.*;
import ru.practicum.explorewithme.models.user.UserShortDto;

import java.util.List;

public interface PrivateSubscriptionService {
    SubscriptionRequestDto addSubscribe(Long followerId, Long publisherId, NewSubscriptionRequest newRequest);

    void revokeRequestBySubscriber(Long followerId, Long subscriptionId);

    void cancelRequestByPublisher(Long publisherId, Long subscriptionId);

    void acceptSubscribe(long publisherId, long friendshipId, boolean friendship, FriendshipGroup group);

    List<SubscriptionRequestDto> getIncomingSubscriptions(long followerId, SubscriptionStatus status, int from, int size);

    List<SubscriptionRequestDto> getOutgoingSubscriptions(long userId, SubscriptionStatus status, int from, int size);

    void addNewGroup(Long userId, NewGroupDto groupDto);

    SubscriptionRequestDto getSubscription(Long userId, Long subscriptionId);
}
