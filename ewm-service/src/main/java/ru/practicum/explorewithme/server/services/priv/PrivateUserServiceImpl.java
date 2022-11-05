package ru.practicum.explorewithme.server.services.priv;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.models.user.UserShortDto;
import ru.practicum.explorewithme.models.user.UserWithSubscriptionDto;
import ru.practicum.explorewithme.server.exceptions.notfound.FollowerNotFoundException;
import ru.practicum.explorewithme.server.exceptions.notfound.UserNotFoundException;
import ru.practicum.explorewithme.server.models.User;
import ru.practicum.explorewithme.server.repositories.FollowersRepository;
import ru.practicum.explorewithme.server.repositories.UserRepository;
import ru.practicum.explorewithme.server.utils.mappers.UserMapper;

import java.util.List;

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
        return null;
    }

    @Override
    public List<UserShortDto> getFollowers(long followerId, long userId, boolean friends, int from, int size) {
        return null;
    }
}
