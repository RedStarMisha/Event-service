package ru.practicum.explorewithme.server.utils.mappers;

import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.models.subscription.*;
import ru.practicum.explorewithme.models.subscription.group.FriendshipGroup;
import ru.practicum.explorewithme.models.subscription.group.GroupDto;
import ru.practicum.explorewithme.server.models.Follower;
import ru.practicum.explorewithme.server.models.Group;
import ru.practicum.explorewithme.server.models.SubscriptionRequest;
import ru.practicum.explorewithme.server.models.User;

import java.time.LocalDateTime;

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
                new SubscriptionRequest(newRequest.isFriendship(), follower, publisher,
                        LocalDateTime.now(), SubscriptionStatus.FOLLOWER);
    }

    public static Follower toFollower(SubscriptionRequest request, boolean friendship, FriendshipGroup group) {
        Group group1 = friendship ? new Group(request.getPublisher(), FriendshipGroup.FRIENDS_ALL.name()) :
                new Group(request.getPublisher(), FriendshipGroup.FOLLOWER.name());
        return new Follower(group1, request.getPublisher(), request.getFollower(), request);
    }

    public static FollowerDto toFollowerDto(Follower follower) {
        return new FollowerDto(follower.getId(), follower.getGroup().getTitle(), follower.getAdded(),
                follower.getFollower().getId(), follower.getRequest().getId());
    }

    public static GroupDto toGroupDto(Group group) {
        return new GroupDto(group.getId(), group.getUser().getId(), group.getTitle());
    }
}
