package ru.practicum.explorewithme.server.services.priv;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.models.subscription.NewSubscriptionRequest;
import ru.practicum.explorewithme.models.subscription.SubscriptionRequestDto;
import ru.practicum.explorewithme.models.subscription.SubscriptionStatus;
import ru.practicum.explorewithme.server.exceptions.notfound.SubscriptionNotFoundException;
import ru.practicum.explorewithme.server.exceptions.notfound.UserNotFoundException;
import ru.practicum.explorewithme.server.exceptions.requestcondition.RequestConditionException;
import ru.practicum.explorewithme.server.models.SubscriptionRequest;
import ru.practicum.explorewithme.server.models.User;
import ru.practicum.explorewithme.server.repositories.SubscriptionRepository;
import ru.practicum.explorewithme.server.repositories.UserRepository;
import ru.practicum.explorewithme.server.utils.mappers.SubscriptionMapper;

import java.util.List;
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


    @Override
    public SubscriptionRequestDto addSubscribe(Long followerId, Long publisherId, NewSubscriptionRequest newRequest) {
        User follower = userRepository.findById(followerId).orElseThrow(() -> new UserNotFoundException(followerId));
        User publisher = userRepository.findById(publisherId).orElseThrow(() -> new UserNotFoundException(publisherId));

        subscriptionRepository.findByFollower_IdAndPublisher_Id(followerId, publisherId)
                .orElseThrow(() -> new RequestConditionException("Запрос на подписку уже отправлялся"));

        SubscriptionRequest request = toSubscriptionRequest(newRequest, publisher, follower);

        request = subscriptionRepository.save(request);

        log.info("SubscriptionRequest {} добавлен", request);
        return toSubscriptionDto(request);
    }

    @Override
    public void revokeRequestBySubscriber(Long followerId, Long publisherId) {
    }

    @Override
    public void cancelRequestByPublisher(Long followerId, Long publisherId) {

    }

    @Override
    public void acceptFriendship(long publisherId, long friendshipId) {
        userRepository.findById(publisherId).orElseThrow(() -> new UserNotFoundException(publisherId));

        SubscriptionRequest request = subscriptionRepository.findByIdAndPublisher_Id(friendshipId, publisherId)
                .orElseThrow(() -> new SubscriptionNotFoundException(friendshipId));

        if (request.getStatus() != SubscriptionStatus.WAITING) {
            throw new RequestConditionException("Нет заявки на дружбу с id = " + friendshipId);
        }

        request.setStatus(SubscriptionStatus.FRIENDSHIP);

        log.info("Добавлен друг по заявке {}", request);

        subscriptionRepository.save(request);
    }

    @Override
    public List<SubscriptionRequestDto> getSubscriptions(long followerId, @Nullable SubscriptionStatus status, int from,
                                                         int size) {
        userRepository.findById(followerId).orElseThrow(() -> new UserNotFoundException(followerId));
        List<SubscriptionRequest> list;

        if (status == null) {
            list =  subscriptionRepository.findAllByFollower_Id(followerId, makePageable(from, size));
        } else if (status == SubscriptionStatus.SUBSCRIPTION) {
            list = subscriptionRepository.findSubscription(followerId, makePageable(from, size));
        } else {
            list =  subscriptionRepository.findAllByFollower_IdAndStatus(followerId, status, makePageable(from, size));
        }
        return list.stream().map(SubscriptionMapper::toSubscriptionDto).collect(Collectors.toList());
    }
}
