package ru.practicum.explorewithme.server.services.priv;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.models.subscription.FriendshipGroup;
import ru.practicum.explorewithme.models.user.UserShortDto;
import ru.practicum.explorewithme.models.user.UserWithSubscriptionDto;
import ru.practicum.explorewithme.server.exceptions.notfound.FollowerNotFoundException;
import ru.practicum.explorewithme.server.exceptions.notfound.UserNotFoundException;
import ru.practicum.explorewithme.server.exceptions.requestcondition.RequestConditionException;
import ru.practicum.explorewithme.server.models.Follower;
import ru.practicum.explorewithme.server.models.User;
import ru.practicum.explorewithme.server.repositories.FollowersRepository;
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
    @Override
    public UserWithSubscriptionDto getUser(long followerId, long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        userRepository.findById(followerId).orElseThrow(() -> new UserNotFoundException(userId));
        followersRepository.findByPublisher_IdAndFollower_Id(userId, followerId)
                .orElseThrow(() -> new FollowerNotFoundException(followerId));

        return UserMapper.toUserWithSubscriptionDto(user);
    }

    @Override
    public List<UserShortDto> getFollowing(long followerId, long userId, boolean friends, int from, int size) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        if (followerId == userId && friends) {
            return followersRepository.findAllByFollower_IdAndGroupNotNull(followerId).stream().map(Follower::getFollower).map(UserMapper::toUserShort)
                    .collect(Collectors.toList());
        }

        User follower = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        if (!user.getFriends().contains(follower)) {
            throw new RequestConditionException("Отказано в доступе. Доступно только для друзей пользователя id =" + userId);
        }
        return followersRepository.findAllByFollower_Id(followerId).stream().map(Follower::getFollower)
                .map(UserMapper::toUserShort).collect(Collectors.toList());
    }

    @Override
    public List<UserShortDto> getFollowers(long followerId, long userId, boolean friends, FriendshipGroup group, int from, int size) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        if (followerId == userId && friends) {
            List<Follower> followers = group == null ? followersRepository.findAllByPublisher_IdAndGroupNotNull(userId) :
                    followersRepository.findAllByPublisher_IdAndGroupIs(userId, group);
            return followers.stream().map(Follower::getFollower).map(UserMapper::toUserShort).collect(Collectors.toList());
        }

        User follower = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        if (!user.getFriends().contains(follower)) {
            throw new RequestConditionException("Отказано в доступе. Доступно только для друзей пользователя id =" + userId);
        }

        return  user.getFollowers().stream().map(Follower::getFollower).map(UserMapper::toUserShort)
                .collect(Collectors.toList());
    }
}
