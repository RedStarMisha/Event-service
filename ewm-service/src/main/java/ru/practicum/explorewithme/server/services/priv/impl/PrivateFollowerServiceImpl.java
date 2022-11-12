package ru.practicum.explorewithme.server.services.priv.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.models.subscription.FollowerDto;
import ru.practicum.explorewithme.models.subscription.UpdateFollowerDto;
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
import ru.practicum.explorewithme.server.services.priv.PrivateFollowerService;
import ru.practicum.explorewithme.server.utils.mappers.SubscriptionMapper;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.explorewithme.server.utils.ServerUtil.makePageable;
import static ru.practicum.explorewithme.server.utils.mappers.SubscriptionMapper.toFollowerDto;
import static ru.practicum.explorewithme.server.utils.mappers.UserMapper.toUserWithSubscriptionDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrivateFollowerServiceImpl implements PrivateFollowerService {

    private final UserRepository userRepository;
    private final FollowersRepository followersRepository;
    private final GroupRepository groupRepository;

    @Override
    public UserWithSubscriptionDto getUser(long followerId, long userId) {
        log.info("Запрос пользователя с id={} фолловером с id={}", userId, followerId);
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        userRepository.findById(followerId).orElseThrow(() -> new UserNotFoundException(followerId));
        followersRepository.findByPublisher_IdAndFollower_Id(userId, followerId)
                .orElseThrow(() -> new FollowerNotFoundException("userFollowerId = " + followerId + " and userId = " + userId));

        user.setFriends(followersRepository.getFriendsAmount(userId));
        user.setFollowers(followersRepository.getFollowersAmount(userId));

        UserWithSubscriptionDto user1 = toUserWithSubscriptionDto(user);
        log.info("Пользователь {} запрошен", user1);

        return user1;
    }

    @Override
    public List<FollowerDto> getFollowing(long userFollowerId, long userId, boolean friends, int from, int size) {
        log.info("Запрос подписок пользователя с id={} фолловером с id={}", userId, userFollowerId);

        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        userRepository.findById(userFollowerId).orElseThrow(() -> new UserNotFoundException(userFollowerId));
        Pageable page = makePageable(from, size);

        followersRepository.getFollowerWithStateFriendship(userId, userFollowerId).orElseThrow(() ->
                new RequestConditionException("Отказано в доступе. Доступно только для друзей пользователя id =" + userId));

        List<Follower> followings = friends ? followersRepository.findFollowingWithStatusFollower(userId, page) :
                followersRepository.findFollowingByUserIdWithStatusNotFollower(userId, page);

        return followings.stream().map(SubscriptionMapper::toFollowerDto).collect(Collectors.toList());
    }

    @Override
    public List<FollowerDto> getFollowers(long userFollowerId, long userId, boolean friends, int from, int size) {
        log.info("Запрос подписчиков пользователя с id={} фолловером с id={}", userId, userFollowerId);

        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        userRepository.findById(userFollowerId).orElseThrow(() -> new UserNotFoundException(userFollowerId));
        Pageable page = makePageable(from, size);

        followersRepository.getFollowerWithStateFriendship(userId, userFollowerId).orElseThrow(() ->
                new RequestConditionException("Отказано в доступе. Доступно только для друзей пользователя id =" + userId));

        List<Follower> followers;
        if (friends) {
            followers = followersRepository.findAllFollowersWithStatusFriend(userId, page);
        } else {
            followers = followersRepository.findFollowersWithStatusFollower(userId, page);
        }
        log.info("Подписчики пользователя с id={} получены", userId);

        return followers.stream().map(SubscriptionMapper::toFollowerDto).collect(Collectors.toList());
    }

    @Override
    public FollowerDto updateFollower(long publisherId, long followerId, UpdateFollowerDto updFollower) {
        log.info("Обновить профиль подписки c id={} на параметры {}", followerId, updFollower);
        userRepository.findById(publisherId).orElseThrow(() -> new UserNotFoundException(publisherId));
        Follower follower = followersRepository.findByIdAndGroupNotFollower(followerId).orElseThrow(() ->
                new FollowerNotFoundException(followerId));

        Group group = groupRepository.findById(updFollower.getGroupId()).orElseThrow(() ->
                new GroupNotFoundException(updFollower.getGroupId()));

        follower.setGroup(group);

        log.info("Профиль подписки с id={} обновлен", followerId);
        return toFollowerDto(followersRepository.save(follower));
    }

    @Override
    public List<FollowerDto> getOwnFollowing(long userId, boolean friends, int from, int size) {
        log.info("Запрос своих подписок пользователем с id={} и параметром friends={}", userId, friends);

        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        List<Follower> followers;
        if (friends) {
            followers = followersRepository.findFollowingByUserIdWithStatusNotFollower(userId, makePageable(from, size));
        } else {
            followers = followersRepository.findFollowingWithStatusFollower(userId, makePageable(from, size));
        }

        log.info("Подписки пользователя с id={} и параметром friends={} получены", userId, friends);
        return followers.stream().map(SubscriptionMapper::toFollowerDto).collect(Collectors.toList());
    }

    @Override
    public List<FollowerDto> getOwnFollowers(long userId, boolean friends, Long groupId, int from, int size) {
        log.info("Запрос своих подписчиков пользователем с id={} и параметрами friends={} groupId={}", userId, friends,
                groupId);

        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Pageable page = makePageable(from, size);

        if (!friends) {
            log.info("Подписчики пользователя с id={} получены", userId);
            return followersRepository.findFollowersWithStatusFollower(userId, page).stream()
                    .map(SubscriptionMapper::toFollowerDto).collect(Collectors.toList());
        }

        List<Follower> friendsList = groupId == null ? followersRepository.findAllFollowersWithStatusFriend(userId, page) :
                followersRepository.findFollowersWithGroup(userId, groupId, page);

        log.info("Друзья пользователя с id={} и параметрами groupId={} получены", userId, groupId);
        return friendsList.stream().map(SubscriptionMapper::toFollowerDto).collect(Collectors.toList());
    }
}
