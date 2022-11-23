package ru.practicum.explorewithme.server.utils.mappers;

import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.models.subscription.FollowerDto;
import ru.practicum.explorewithme.models.subscription.NewSubscriptionRequest;
import ru.practicum.explorewithme.models.subscription.SubscriptionStatus;
import ru.practicum.explorewithme.models.subscription.group.GroupDto;
import ru.practicum.explorewithme.server.models.Follower;
import ru.practicum.explorewithme.server.models.Group;
import ru.practicum.explorewithme.server.models.SubscriptionRequest;
import ru.practicum.explorewithme.server.models.User;

import java.time.LocalDateTime;


@Component
public class SubscriptionMapper {

    public static SubscriptionRequest toSubscriptionRequest(NewSubscriptionRequest newRequest, User follower, User publisher) {
        return newRequest.isFriendship() ?
                new SubscriptionRequest(newRequest.isFriendship(), follower, publisher, SubscriptionStatus.WAITING) :
                new SubscriptionRequest(newRequest.isFriendship(), follower, publisher,
                        LocalDateTime.now(), SubscriptionStatus.FOLLOWER);
    }

    public static FollowerDto toFollowerDto(Follower follower) {
        return new FollowerDto(follower.getId(), follower.getGroup().getTitle(), follower.getAdded(),
                follower.getFollower().getId(), follower.getRequest().getId());
    }

    public static GroupDto toGroupDto(Group group) {
        return new GroupDto(group.getId(), group.getUser().getId(), group.getTitle());
    }
}
