package ru.practicum.explorewithme.server.services.priv;

import ru.practicum.explorewithme.models.subscription.FollowerDto;
import ru.practicum.explorewithme.models.subscription.UpdateFollowerDto;
import ru.practicum.explorewithme.models.user.UserWithSubscriptionDto;

import java.util.List;

public interface PrivateFollowerService {
    UserWithSubscriptionDto getUser(long followerId, long userId);

    List<FollowerDto> getFollowing(long userFollowerId, long userId, boolean friends, int from, int size);

    List<FollowerDto> getFollowers(long followerId, long userId, boolean friends, int from, int size);

    FollowerDto updateFollower(long publisherId, long followerId, UpdateFollowerDto updFollower);

    List<FollowerDto> getOwnFollowing(long userId, boolean friends, int from, int size);

    List<FollowerDto> getOwnFollowers(long userId, boolean friends, Long groupId, int from, int size);
}
