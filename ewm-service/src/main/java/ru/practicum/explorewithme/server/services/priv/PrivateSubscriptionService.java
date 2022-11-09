package ru.practicum.explorewithme.server.services.priv;

import ru.practicum.explorewithme.models.subscription.NewSubscriptionRequest;
import ru.practicum.explorewithme.models.subscription.SubscriptionRequestDto;
import ru.practicum.explorewithme.models.subscription.SubscriptionStatus;
import ru.practicum.explorewithme.models.subscription.group.FriendshipGroup;
import ru.practicum.explorewithme.models.subscription.group.GroupDto;
import ru.practicum.explorewithme.models.subscription.group.NewGroupDto;

import java.util.List;

public interface PrivateSubscriptionService {
    SubscriptionRequestDto addSubscribe(Long followerId, Long publisherId, NewSubscriptionRequest newRequest);

    SubscriptionRequestDto cancelSubscription(Long userId, Long subscriptionId);

    SubscriptionRequestDto acceptSubscribe(long publisherId, long friendshipId, boolean friendship, FriendshipGroup group);

    List<SubscriptionRequestDto> getIncomingSubscriptions(long followerId, SubscriptionStatus status, int from, int size);

    List<SubscriptionRequestDto> getOutgoingSubscriptions(long userId, SubscriptionStatus status, int from, int size);

    void addNewGroup(Long userId, NewGroupDto groupDto);

    SubscriptionRequestDto getSubscriptionById(Long userId, Long subscriptionId);

    List<GroupDto> getGroups(Long userId);
}
