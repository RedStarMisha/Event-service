package ru.practicum.explorewithme.server.services.priv;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.models.subscription.FriendshipGroup;
import ru.practicum.explorewithme.models.subscription.NewSubscriptionRequest;
import ru.practicum.explorewithme.models.subscription.SubscriptionRequestDto;
import ru.practicum.explorewithme.models.subscription.SubscriptionStatus;
import ru.practicum.explorewithme.models.user.UserShortDto;
import ru.practicum.explorewithme.server.exceptions.notfound.SubscriptionNotFoundException;
import ru.practicum.explorewithme.server.exceptions.notfound.UserNotFoundException;
import ru.practicum.explorewithme.server.exceptions.requestcondition.RequestConditionException;
import ru.practicum.explorewithme.server.models.SubscriptionRequest;
import ru.practicum.explorewithme.server.models.User;
import ru.practicum.explorewithme.server.repositories.FollowersRepository;
import ru.practicum.explorewithme.server.repositories.SubscriptionRepository;
import ru.practicum.explorewithme.server.repositories.UserRepository;
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


    @Override
    public SubscriptionRequestDto addSubscribe(Long followerId, Long publisherId, NewSubscriptionRequest newRequest) {
        User follower = userRepository.findById(followerId).orElseThrow(() -> new UserNotFoundException(followerId));
        User publisher = userRepository.findById(publisherId).orElseThrow(() -> new UserNotFoundException(publisherId));

        Optional<SubscriptionRequest> request1 = subscriptionRepository.findByFollower_IdAndPublisher_Id(followerId,
                        publisherId);
               // .orElseThrow(() -> new RequestConditionException("Запрос на подписку уже отправлялся"));

        SubscriptionRequest request = toSubscriptionRequest(newRequest, publisher, follower);

        request = subscriptionRepository.save(request);

        log.info("SubscriptionRequest {} добавлен", request);
        return toSubscriptionDto(request);
    }

    @Override
    public void revokeRequestBySubscriber(Long followerId, Long subscriptionId) {
        userRepository.findById(followerId).orElseThrow(() -> new UserNotFoundException(followerId));
        SubscriptionRequest request = subscriptionRepository.findByIdAndFollower_Id(subscriptionId, followerId)
                .orElseThrow(() -> new SubscriptionNotFoundException(subscriptionId));

        if (request.getStatus() != SubscriptionStatus.WAITING) {
            throw new RequestConditionException("Заявка уже обработана, ее нельзя отменить");
        }

        request.setStatus(SubscriptionStatus.REVOKE);
        request.setUpdated(LocalDateTime.now());

        subscriptionRepository.save(request);
    }

    @Override
    public void cancelRequestByPublisher(Long publisherId, Long subscriptionId) {
        userRepository.findById(publisherId).orElseThrow(() -> new UserNotFoundException(publisherId));

        SubscriptionRequest request = subscriptionRepository.findByIdAndPublisher_IdAndStatusIsNot(subscriptionId,
            publisherId, SubscriptionStatus.REVOKE).orElseThrow(() -> new SubscriptionNotFoundException(subscriptionId));

        if (request.getStatus() != SubscriptionStatus.WAITING) {
            throw new RequestConditionException("Заявка уже обработана, ее нельзя отменить");
        }

        request.setStatus(SubscriptionStatus.CANCELED);
        request.setUpdated(LocalDateTime.now());

        subscriptionRepository.save(request);
    }

    @Override
    public void acceptSubscribe(long publisherId, long subscriptionId, boolean friendship, FriendshipGroup group) {
        userRepository.findById(publisherId).orElseThrow(() -> new UserNotFoundException(publisherId));

        SubscriptionRequest request = subscriptionRepository.findByIdAndPublisher_IdAndStatusIs(subscriptionId,
            publisherId, SubscriptionStatus.WAITING).orElseThrow(() -> new SubscriptionNotFoundException(subscriptionId));

        request.setStatus(SubscriptionStatus.CONSIDER);
        request.setUpdated(LocalDateTime.now());

        log.info("Добавлен пользователь по заявке {}", request);

        followersRepository.save(toFollower(request, friendship, group));
    }

    @Override
    public List<SubscriptionRequestDto> getIncomingSubscriptions(long followerId, @Nullable SubscriptionStatus status,
                                                                 int from, int size) {
        userRepository.findById(followerId).orElseThrow(() -> new UserNotFoundException(followerId));
        List<SubscriptionRequest> list;

        if (status == null) {
            list =  subscriptionRepository.findAllByFollower_Id(followerId, makePageable(from, size));
        } else {
            list =  subscriptionRepository.findAllByFollower_IdAndStatus(followerId, status, makePageable(from, size));
        }

        return list.stream().map(SubscriptionMapper::toSubscriptionDto).collect(Collectors.toList());
    }

    @Override
    public List<SubscriptionRequestDto> getOutgoingSubscriptions(long userId, SubscriptionStatus status, int from, int size) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        List<SubscriptionRequest> list;

        if (status == null) {
            list =  subscriptionRepository.findAllByPublisher(userId, makePageable(from, size));
        } else {
            list =  subscriptionRepository.findAllByPublisher_IdAndStatusIs(userId, status, makePageable(from, size));
        }

        return list.stream().map(SubscriptionMapper::toSubscriptionDto).collect(Collectors.toList());
    }
}
