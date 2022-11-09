package ru.practicum.explorewithme.server.services.priv;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.models.subscription.*;
import ru.practicum.explorewithme.models.subscription.group.FriendshipGroup;
import ru.practicum.explorewithme.models.subscription.group.GroupDto;
import ru.practicum.explorewithme.models.subscription.group.NewGroupDto;
import ru.practicum.explorewithme.server.exceptions.notfound.SubscriptionNotFoundException;
import ru.practicum.explorewithme.server.exceptions.notfound.UserNotFoundException;
import ru.practicum.explorewithme.server.exceptions.requestcondition.RequestConditionException;
import ru.practicum.explorewithme.server.models.Follower;
import ru.practicum.explorewithme.server.models.Group;
import ru.practicum.explorewithme.server.models.SubscriptionRequest;
import ru.practicum.explorewithme.server.models.User;
import ru.practicum.explorewithme.server.repositories.FollowersRepository;
import ru.practicum.explorewithme.server.repositories.GroupRepository;
import ru.practicum.explorewithme.server.repositories.SubscriptionRepository;
import ru.practicum.explorewithme.server.repositories.UserRepository;
import ru.practicum.explorewithme.server.utils.mappers.SubscriptionMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.practicum.explorewithme.server.utils.ServerUtil.makePageable;
import static ru.practicum.explorewithme.server.utils.mappers.SubscriptionMapper.toSubscriptionDto;
import static ru.practicum.explorewithme.server.utils.mappers.SubscriptionMapper.toSubscriptionRequest;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
@Transactional
public class PrivateSubscriptionServiceImpl implements PrivateSubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final FollowersRepository followersRepository;
    private final GroupRepository groupRepository;


    @Override
    public SubscriptionRequestDto addSubscribe(Long followerId, Long publisherId, NewSubscriptionRequest newRequest) {
        User follower = userRepository.findById(followerId).orElseThrow(() -> new UserNotFoundException(followerId));
        User publisher = userRepository.findById(publisherId).orElseThrow(() -> new UserNotFoundException(publisherId));

        Optional<SubscriptionRequest> oldRequest =
                subscriptionRepository.findByFollower_IdAndPublisher_IdAndStatusNot(followerId, publisherId, SubscriptionStatus.REVOKE);

        if (oldRequest.isPresent()) {
            throw new RequestConditionException("Запрос на подписку уже отправлялся");
        }

        SubscriptionRequest request = toSubscriptionRequest(newRequest, publisher, follower);

        request = subscriptionRepository.save(request);

        Group group = groupRepository.findByUser_IdAndTitleIgnoreCase(publisherId, "FOLLOWER").get();
        Follower newFollower = new Follower(group, request.getPublisher(), request.getFollower(), request);
        followersRepository.save(newFollower);

        log.info("SubscriptionRequest {} добавлен", request);
        return toSubscriptionDto(request);
    }

    @Override
    public SubscriptionRequestDto getSubscriptionById(Long userId, Long subscriptionId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return subscriptionRepository.findSubscriptionByIdAndUserId(subscriptionId, userId).map(SubscriptionMapper::toSubscriptionDto)
                .orElseThrow(() -> new SubscriptionNotFoundException(subscriptionId));
    }

    @Override
    public SubscriptionRequestDto cancelSubscription(Long userId, Long subscriptionId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        SubscriptionRequest request =
                subscriptionRepository.findByIdAndUserIdAndStatusIsNot(subscriptionId, SubscriptionStatus.REVOKE, userId)
                        .orElseThrow(() -> new SubscriptionNotFoundException(subscriptionId));

        if (request.getPublisher().getId() == userId) {
            request.setStatus(SubscriptionStatus.CANCELED_BY_PUBLISHER);
            log.info("SubscriptionRequest {} отклонен publisherом", request);
        } else {
            request.setStatus(SubscriptionStatus.CANCELED_BY_FOLLOWER);
            log.info("SubscriptionRequest {} отклонен followerом", request);
        }

        request.setUpdated(LocalDateTime.now());
        request = subscriptionRepository.save(request);

        Follower follower = followersRepository.findByRequest_Id(subscriptionId).get();
        followersRepository.delete(follower);

        return toSubscriptionDto(request);
    }

    @Override
    public SubscriptionRequestDto acceptSubscribe(long publisherId, long subscriptionId, boolean friendship,
                                                  FriendshipGroup group1) {
        userRepository.findById(publisherId).orElseThrow(() -> new UserNotFoundException(publisherId));

        SubscriptionRequest request = subscriptionRepository.findByIdAndPublisher_IdAndStatusIs(subscriptionId,
                publisherId, SubscriptionStatus.WAITING).orElseThrow(() -> new SubscriptionNotFoundException(subscriptionId));

        if (!friendship) {
            request.setStatus(SubscriptionStatus.FOLLOWER);
            request.setUpdated(LocalDateTime.now());
            log.info("Заявка на дружбу по заявке {} отклонена", request);
            return toSubscriptionDto(subscriptionRepository.save(request));
        }

        request.setStatus(SubscriptionStatus.CONSIDER);
        request.setUpdated(LocalDateTime.now());

        Follower follower = followersRepository.findByRequest_Id(subscriptionId).get();
        Group group = groupRepository.findByUser_IdAndTitleIgnoreCase(publisherId, "FRIENDS_ALL").get();

        follower.setGroup(group);

        log.info("Заявка на дружбу по заявке {} подтверждена", request);

        followersRepository.save(follower);
        return toSubscriptionDto(request);
    }

    @Override
    public List<SubscriptionRequestDto> getIncomingSubscriptions(long followerId, @Nullable SubscriptionStatus status,
                                                                 int from, int size) {
        userRepository.findById(followerId).orElseThrow(() -> new UserNotFoundException(followerId));
        List<SubscriptionRequest> list;

        if (status == null) {
            list = subscriptionRepository.findAllByFollower_Id(followerId, makePageable(from, size));
        } else {
            list = subscriptionRepository.findAllByFollower_IdAndStatus(followerId, status, makePageable(from, size));
        }

        return list.stream().map(SubscriptionMapper::toSubscriptionDto).collect(Collectors.toList());
    }

    @Override
    public List<SubscriptionRequestDto> getOutgoingSubscriptions(long userId, SubscriptionStatus status, int from, int size) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        List<SubscriptionRequest> list;

        if (status == null) {
            list = subscriptionRepository.findAllByPublisher(userId, makePageable(from, size));
        } else {
            list = subscriptionRepository.findAllByPublisher_IdAndStatusIs(userId, status, makePageable(from, size));
        }

        return list.stream().map(SubscriptionMapper::toSubscriptionDto).collect(Collectors.toList());
    }

    @Override
    public void addNewGroup(Long userId, NewGroupDto groupDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Optional<Group> group = groupRepository.findByUser_IdAndTitleIgnoreCase(userId, groupDto.getTitle());
        if (group.isPresent()) {
            throw new RequestConditionException("Такая группа уже существует");
        }
        groupRepository.save(new Group(user, groupDto.getTitle().toUpperCase()));
    }

    @Override
    public List<GroupDto> getGroups(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return groupRepository.findAllByUser_Id(userId).stream().map(SubscriptionMapper::toGroupDto)
                .collect(Collectors.toList());
    }
}
