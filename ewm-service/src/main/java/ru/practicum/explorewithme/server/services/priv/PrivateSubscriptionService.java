package ru.practicum.explorewithme.server.services.priv;

import ru.practicum.explorewithme.models.subscription.NewSubscriptionRequest;
import ru.practicum.explorewithme.models.subscription.SubscriptionRequestDto;
import ru.practicum.explorewithme.models.subscription.SubscriptionStatus;

import java.util.List;

public interface PrivateSubscriptionService {
    SubscriptionRequestDto addSubscribe(Long followerId, Long publisherId, NewSubscriptionRequest newRequest);

    void revokeRequestBySubscriber(Long followerId, Long publisherId);

    void cancelRequestByPublisher(Long followerId, Long publisherId);

    void acceptFriendship(long publisherId, long friendshipId);

    List<SubscriptionRequestDto> getSubscriptions(long followerId, SubscriptionStatus status, int from, int size);
}
