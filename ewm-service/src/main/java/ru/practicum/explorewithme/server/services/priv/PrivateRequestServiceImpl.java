package ru.practicum.explorewithme.server.services.priv;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.models.event.State;
import ru.practicum.explorewithme.models.request.ParticipationRequestDto;
import ru.practicum.explorewithme.models.request.RequestStatus;
import ru.practicum.explorewithme.server.exceptions.notfound.EventNotFoundException;
import ru.practicum.explorewithme.server.exceptions.notfound.RequestNotFoundException;
import ru.practicum.explorewithme.server.exceptions.notfound.UserNotFoundException;
import ru.practicum.explorewithme.server.exceptions.requestcondition.RequestConditionException;
import ru.practicum.explorewithme.server.models.Event;
import ru.practicum.explorewithme.server.models.Request;
import ru.practicum.explorewithme.server.models.User;
import ru.practicum.explorewithme.server.repositories.EventRepository;
import ru.practicum.explorewithme.server.repositories.RequestRepository;
import ru.practicum.explorewithme.server.repositories.UserRepository;
import ru.practicum.explorewithme.server.utils.mappers.RequestMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.explorewithme.server.utils.mappers.RequestMapper.toRequestDto;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
@Transactional
public class PrivateRequestServiceImpl implements PrivateRequestService {

    private final UserRepository userRepository;
    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;

    @Override
    public List<ParticipationRequestDto> getEventRequestsByUser(long userId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        log.info("Пользователем с id = {} запрошен список своих заявок", userId);
        return requestRepository.findAllByRequestor_Id(userId).stream().map(RequestMapper::toRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto addNewRequestByUser(long userId, long eventId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        Event event = eventRepository.findByIdAndState(eventId, State.PUBLISHED)
                .orElseThrow(() -> new EventNotFoundException(eventId));

        if (event.getInitiator().getId() == userId) {
            throw new RequestConditionException("Пользователь не может подать заявку на участие в своем событии");
        }

        if (event.getEventDate().isBefore(LocalDateTime.now())) {
            throw new RequestConditionException("Событие уже прошло");
        }

        requestRepository.findByRequestor_IdAndEvent_Id(userId, eventId).ifPresent((request) -> {
            throw new RequestConditionException(String.format("Запрос на участие в событии %s уже отправлен",
                    event.getTitle()));
        });

        if (event.getParticipantLimit() != 0 && event.getParticipantLimit() <= event.getNumberConfirmed()) {
            throw new RequestConditionException("Лимит участников события достигнут");
        }

        Request request;
        if (event.isModeration()) {
            request = Request.makePending(user, event);
        } else {
            request = Request.makeConfirmed(user, event);
            eventRepository.addConfirmedRequest(eventId);
        }

        return toRequestDto(requestRepository.save(request));
    }

    @Override
    public ParticipationRequestDto cancelUserRequest(long userId, long requestId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Request request = requestRepository.findById(requestId).orElseThrow(() -> new RequestNotFoundException(requestId));
        request.setStatus(RequestStatus.CANCELED);
        return toRequestDto(requestRepository.save(request));
    }
}
