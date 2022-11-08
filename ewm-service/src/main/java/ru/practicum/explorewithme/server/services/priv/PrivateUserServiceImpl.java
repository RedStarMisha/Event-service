package ru.practicum.explorewithme.server.services.priv;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.models.subscription.FriendshipGroup;
import ru.practicum.explorewithme.models.user.UserShortDto;
import ru.practicum.explorewithme.models.user.UserWithSubscriptionDto;
import ru.practicum.explorewithme.server.exceptions.notfound.FollowerNotFoundException;
import ru.practicum.explorewithme.server.exceptions.notfound.GroupNotFoundException;
import ru.practicum.explorewithme.server.exceptions.notfound.UserNotFoundException;
import ru.practicum.explorewithme.server.exceptions.requestcondition.RequestConditionException;
import ru.practicum.explorewithme.server.models.Follower;
import ru.practicum.explorewithme.server.models.Group;
import ru.practicum.explorewithme.server.models.User;
import ru.practicum.explorewithme.server.repositories.FollowersRepository;
import ru.practicum.explorewithme.server.repositories.GroupRepository;
import ru.practicum.explorewithme.server.repositories.UserRepository;
import ru.practicum.explorewithme.server.utils.mappers.UserMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class PrivateUserServiceImpl implements PrivateUserService {

    private final UserRepository userRepository;
    private final FollowersRepository followersRepository;

    private final GroupRepository groupRepository;
    @Override
    public UserWithSubscriptionDto getUser(long followerId, long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        userRepository.findById(followerId).orElseThrow(() -> new UserNotFoundException(userId));
        followersRepository.findByPublisher_IdAndFollower_Id(userId, followerId)
                .orElseThrow(() -> new FollowerNotFoundException(followerId));

        user.setFriends(followersRepository.getFriendsAmount(userId));
        user.setFollowers(followersRepository.getFollowersAmount(userId));

        return UserMapper.toUserWithSubscriptionDto(user);
    }

    @Override
    public List<UserShortDto> getFollowing(long followerId, long userId, boolean friends, int from, int size) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        if (followerId == userId && friends) {
            return followersRepository.findFollowing(userId).stream().map(Follower::getFollower).map(UserMapper::toUserShort)
                    .collect(Collectors.toList());
        }

        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        followersRepository.checkFriendship(userId, followerId).orElseThrow(() ->
                new RequestConditionException("Отказано в доступе. Доступно только для друзей пользователя id =" + userId));

        return followersRepository.findFollowers(followerId).stream().map(Follower::getFollower)
                .map(UserMapper::toUserShort).collect(Collectors.toList());
    }

    @Override
    public List<UserShortDto> getFollowers(long followerId, long userId, boolean friends, String group, int from, int size) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Group group1 = checkGroup(userId, group, friends);

        List<Follower> followers = followersRepository.findAllByPublisher_IdAndGroupIs(userId, group1);
        List<UserShortDto> users = followers.stream().map(Follower::getFollower).map(UserMapper::toUserShort)
                .collect(Collectors.toList());

        if (followerId == userId && friends) {
            return users;
        }

        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        followersRepository.checkFriendship(userId, followerId).orElseThrow(() ->
                new RequestConditionException("Отказано в доступе. Доступно только для друзей пользователя id =" + userId));

        return users;
    }

    private Group checkGroup(long userId, String group, boolean friends) {
        if (friends == false) {
            return groupRepository.findByUser_IdAndTitleIgnoreCase(userId, FriendshipGroup.FOLLOWER.name()).get();
        }
        if (friends && group == null) {
            return groupRepository.findByUser_IdAndTitleIgnoreCase(userId, FriendshipGroup.FRIENDS_ALL.name()).get();
        }
        return groupRepository.findByUser_IdAndTitleIgnoreCase(userId, group).orElseThrow(() ->
                new GroupNotFoundException(group));
    }
}
