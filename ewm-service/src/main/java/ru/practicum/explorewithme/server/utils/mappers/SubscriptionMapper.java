package ru.practicum.explorewithme.server.utils.mappers;

import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.models.subscription.NewSubscriptionRequest;
import ru.practicum.explorewithme.models.subscription.SubscriptionRequestDto;
import ru.practicum.explorewithme.models.subscription.SubscriptionStatus;
import ru.practicum.explorewithme.server.models.SubscriptionRequest;
import ru.practicum.explorewithme.server.models.User;

import static ru.practicum.explorewithme.server.utils.mappers.UserMapper.toUserShort;

@Component
public class SubscriptionMapper {

    public static SubscriptionRequestDto toSubscriptionDto(SubscriptionRequest request) {
        return new SubscriptionRequestDto(request.getId(), request.isFriendshipRequest(), request.getCreated(),
                request.getStatus(), toUserShort(request.getFollower()), toUserShort(request.getPublisher()));
    }

    public static SubscriptionRequest toSubscriptionRequest(NewSubscriptionRequest newRequest, User follower, User publisher) {
        return newRequest.isFriendship() ?
                new SubscriptionRequest(newRequest.isFriendship(), follower, publisher, SubscriptionStatus.WAITING) :
                new SubscriptionRequest(newRequest.isFriendship(), follower, publisher, SubscriptionStatus.SUBSCRIPTION);
    }
}
