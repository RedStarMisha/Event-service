package ru.practicum.explorewithme.server.utils.mappers;

import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.models.subscription.FriendshipGroup;
import ru.practicum.explorewithme.models.subscription.NewSubscriptionRequest;
import ru.practicum.explorewithme.models.subscription.SubscriptionRequestDto;
import ru.practicum.explorewithme.models.subscription.SubscriptionStatus;
import ru.practicum.explorewithme.server.models.Follower;
import ru.practicum.explorewithme.server.models.Group;
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
        Group group1 = friendship ? new Group(request.getPublisher(), FriendshipGroup.FRIENDS_ALL.name()) :
                new Group(request.getPublisher(), FriendshipGroup.FOLLOWER.name());
        return new Follower(group1, request.getPublisher(), request.getFollower(), request);
    }
}
