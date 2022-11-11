package ru.practicum.explorewithme.server.services.priv.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.models.subscription.NewSubscriptionRequest;
import ru.practicum.explorewithme.models.subscription.SubscriptionRequestDto;
import ru.practicum.explorewithme.models.subscription.SubscriptionStatus;
import ru.practicum.explorewithme.models.subscription.group.GroupDto;
import ru.practicum.explorewithme.models.subscription.group.NewGroupDto;
import ru.practicum.explorewithme.server.exceptions.notfound.FollowerNotFoundException;
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
import ru.practicum.explorewithme.server.services.priv.PrivateSubscriptionService;
import ru.practicum.explorewithme.server.utils.mappers.SubscriptionMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.practicum.explorewithme.server.utils.ServerUtil.makePageable;
import static ru.practicum.explorewithme.server.utils.mappers.SubscriptionMapper.*;

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
        log.info("Заявка на подписку к publisherId={} от followerId={} с параметрами {}", publisherId,
                followerId, newRequest);

        User follower = userRepository.findById(followerId).orElseThrow(() -> new UserNotFoundException(followerId));
        User publisher = userRepository.findById(publisherId).orElseThrow(() -> new UserNotFoundException(publisherId));

        subscriptionRepository.findByFollowerIdAndPublisherIdForAccept(followerId, publisherId).orElseThrow(() ->
                new SubscriptionNotFoundException("Запрос на подписку от followerId=" + followerId + " к publisherId=" +
                        publisherId + " не найден"));

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
        log.info("Получим SubscriptionRequest по subscriptionId={} и userId={}", subscriptionId, userId);
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return subscriptionRepository.findSubscriptionByIdAndUserId(subscriptionId, userId).map(SubscriptionMapper::toSubscriptionDto)
                .orElseThrow(() -> new SubscriptionNotFoundException(subscriptionId));
    }

    @Override
    public SubscriptionRequestDto cancelSubscription(Long userId, Long subscriptionId) {
        log.info("Отменим подписку по subscriptionId={} и userId={}", subscriptionId, userId);

        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        Follower follower = followersRepository.findBySubscriptionIdAndUserId(subscriptionId, userId).orElseThrow(() ->
                new FollowerNotFoundException("Подписка по заявке c subscriptionId=" + subscriptionId + " не найдена"));

        SubscriptionRequest request = follower.getRequest();

        if (follower.getPublisher().getId() == userId) {
            request.setStatus(SubscriptionStatus.CANCELED_BY_PUBLISHER);
            log.info("SubscriptionRequest {} отклонен publisherом", request);
        } else {
            request.setStatus(SubscriptionStatus.CANCELED_BY_FOLLOWER);
            log.info("SubscriptionRequest {} отклонен followerом", request);
        }

        request.setUpdated(LocalDateTime.now());
        request = subscriptionRepository.save(request);

        followersRepository.delete(follower);

        return toSubscriptionDto(request);
    }

    @Override
    public SubscriptionRequestDto acceptSubscribe(long publisherId, long subscriptionId, boolean friendship) {
        log.info("Обработаем заявку на подписку с id={} к publisherId={} и подтверждением дружбы {}", subscriptionId,
                publisherId, friendship);
        userRepository.findById(publisherId).orElseThrow(() -> new UserNotFoundException(publisherId));

        SubscriptionRequest request = subscriptionRepository.findByIdAndPublisher_IdAndStatusIs(subscriptionId,
                publisherId, SubscriptionStatus.WAITING).orElseThrow(() -> new SubscriptionNotFoundException(subscriptionId));

        request.setUpdated(LocalDateTime.now());

        if (!friendship) {
            request.setStatus(SubscriptionStatus.FOLLOWER);
            log.info("Заявка на дружбу по заявке {} отклонена", request);
            return toSubscriptionDto(subscriptionRepository.save(request));
        }

        request.setStatus(SubscriptionStatus.CONSIDER);

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
        log.info("Запрос исходящих заявок на подписку пользователя с id={} и статусом {}", followerId, status);

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
        log.info("Запрос входящих заявок на подписку пользователя с id={} и статусом {}", userId, status);

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
    public GroupDto addNewGroup(Long userId, NewGroupDto groupDto) {
        log.info("Пользователь с id={} добавляет новую группу {}", userId, groupDto);

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Optional<Group> groupOpt = groupRepository.findByUser_IdAndTitleIgnoreCase(userId, groupDto.getTitle());
        if (groupOpt.isPresent()) {
            throw new RequestConditionException("Такая группа уже существует");
        }
        Group group = groupRepository.save(new Group(user, groupDto.getTitle().toUpperCase()));
        return toGroupDto(group);
    }

    @Override
    public List<GroupDto> getGroups(Long userId) {
        log.info("Запрос групп пользователя с id={}", userId);
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        return groupRepository.findAllByUser_Id(userId).stream().map(SubscriptionMapper::toGroupDto)
                .collect(Collectors.toList());
    }
}
