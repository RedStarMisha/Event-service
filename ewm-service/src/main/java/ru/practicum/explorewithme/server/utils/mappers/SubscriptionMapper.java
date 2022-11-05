package ru.practicum.explorewithme.server.utils.mappers;

import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.models.subscription.FriendshipGroup;
import ru.practicum.explorewithme.models.subscription.NewSubscriptionRequest;
import ru.practicum.explorewithme.models.subscription.SubscriptionRequestDto;
import ru.practicum.explorewithme.models.subscription.SubscriptionStatus;
import ru.practicum.explorewithme.server.models.Follower;
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
        return new SubscriptionRequest(newRequest.isFriendship(), follower, publisher, SubscriptionStatus.WAITING);
    }

    public static Follower toFollower(SubscriptionRequest request, boolean friendship, FriendshipGroup group) {
        FriendshipGroup group1 = group == null ? FriendshipGroup.ALL : group;
        return friendship ? new Follower(group1, request.getPublisher(), request.getFollower(), request) :
                new Follower(request.getPublisher(), request.getFollower(), request);
    }
}
