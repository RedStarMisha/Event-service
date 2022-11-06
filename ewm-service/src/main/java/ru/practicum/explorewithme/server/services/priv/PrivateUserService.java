package ru.practicum.explorewithme.server.services.priv;

import ru.practicum.explorewithme.models.subscription.FriendshipGroup;
import ru.practicum.explorewithme.models.user.UserShortDto;
import ru.practicum.explorewithme.models.user.UserWithSubscriptionDto;

import java.util.List;

public interface PrivateUserService {
    UserWithSubscriptionDto getUser(long followerId, long userId);

    List<UserShortDto> getFollowing(long followerId, long userId, boolean friends, int from, int size);

    List<UserShortDto> getFollowers(long followerId, long userId, boolean friends, FriendshipGroup group, int from, int size);
}
