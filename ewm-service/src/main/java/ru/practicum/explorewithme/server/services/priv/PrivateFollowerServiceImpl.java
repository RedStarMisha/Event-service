package ru.practicum.explorewithme.server.services.priv;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import ru.practicum.explorewithme.server.utils.mappers.SubscriptionMapper;
import ru.practicum.explorewithme.server.utils.mappers.UserMapper;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.explorewithme.server.utils.ServerUtil.makePageable;
import static ru.practicum.explorewithme.server.utils.mappers.SubscriptionMapper.toFollowerDto;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class PrivateFollowerServiceImpl implements PrivateFollowerService {

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
    public List<FollowerDto> getFollowing(long userFollowerId, long userId, boolean friends, int from, int size) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        userRepository.findById(userFollowerId).orElseThrow(() -> new UserNotFoundException(userFollowerId));
        Pageable page = makePageable(from, size);

        followersRepository.getFollowerWithStateFriendship(userId, userFollowerId).orElseThrow(() ->
                new RequestConditionException("Отказано в доступе. Доступно только для друзей пользователя id =" + userId));

        List<Follower> followings = friends ? followersRepository.findFollowingWithStatusFollower(userId, page) :
                followersRepository.findFollowingWithStatusFriend(userId, page);

        return followings.stream().map(SubscriptionMapper::toFollowerDto).collect(Collectors.toList());
    }

    @Override
    public List<FollowerDto> getFollowers(long userFollowerId, long userId, boolean friends, int from, int size) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        userRepository.findById(userFollowerId).orElseThrow(() -> new UserNotFoundException(userFollowerId));
        Pageable page = makePageable(from, size);

        followersRepository.getFollowerWithStateFriendship(userId, userFollowerId).orElseThrow(() ->
                new RequestConditionException("Отказано в доступе. Доступно только для друзей пользователя id =" + userId));

        List<Follower> followers = friends ? followersRepository.findAllFollowersWithStatusFriend(userId, page) :
                followersRepository.findFollowersWithStatusFollower(userId, page);

        return followers.stream().map(SubscriptionMapper::toFollowerDto).collect(Collectors.toList());
    }

    @Override
    public FollowerDto updateFollower(long publisherId, long followerId, UpdateFollowerDto updFollower) {
        userRepository.findById(publisherId).orElseThrow(() -> new UserNotFoundException(publisherId));
        Follower follower = followersRepository.findByIdAndGroupFriend(followerId).orElseThrow(() ->
                new FollowerNotFoundException(followerId));

        Group group = groupRepository.findById(updFollower.getGroupId()).orElseThrow(() ->
                new GroupNotFoundException(updFollower.getGroupId()));

        follower.setGroup(group);

        return toFollowerDto(followersRepository.save(follower));
    }

    @Override
    public List<FollowerDto> getOwnFollowing(long userId, boolean friends, int from, int size) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        List<Follower> followers = friends ? followersRepository.findFollowingWithStatusFriend(userId, makePageable(from, size)) :
                followersRepository.findFollowingWithStatusFollower(userId, makePageable(from, size));

        return followers.stream().map(SubscriptionMapper::toFollowerDto).collect(Collectors.toList());
    }

    @Override
    public List<FollowerDto> getOwnFollowers(long userId, boolean friends, Long groupId, int from, int size) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Pageable page = makePageable(from, size);

        if (!friends) {
            return followersRepository.findFollowersWithStatusFollower(userId, page).stream()
                    .map(SubscriptionMapper::toFollowerDto).collect(Collectors.toList());
        }

        List<Follower> friendsList = groupId == null ? followersRepository.findAllFollowersWithStatusFriend(userId, page) :
                followersRepository.findFollowersWithGroup(userId, groupId, page);
        return friendsList.stream().map(SubscriptionMapper::toFollowerDto).collect(Collectors.toList());
    }
}
