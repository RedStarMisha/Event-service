package ru.practicum.explorewithme.server.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.models.ParticipationRequestDto;
import ru.practicum.explorewithme.models.RequestStatus;
import ru.practicum.explorewithme.models.State;
import ru.practicum.explorewithme.models.event.*;
import ru.practicum.explorewithme.server.exceptions.notfound.RequestNotFoundException;
import ru.practicum.explorewithme.server.utils.mappers.EventMapper;
import ru.practicum.explorewithme.server.models.Category;
import ru.practicum.explorewithme.server.models.Event;
import ru.practicum.explorewithme.server.models.Loc;
import ru.practicum.explorewithme.server.models.Request;
import ru.practicum.explorewithme.server.models.User;
import ru.practicum.explorewithme.server.exceptions.notfound.CategoryNotFoundException;
import ru.practicum.explorewithme.server.exceptions.notfound.EventNotFoundException;
import ru.practicum.explorewithme.server.exceptions.notfound.UserNotFoundException;
import ru.practicum.explorewithme.server.exceptions.requestcondition.RequestConditionException;
import ru.practicum.explorewithme.server.repositories.*;
import ru.practicum.explorewithme.server.utils.mappers.RequestMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.practicum.explorewithme.server.utils.mappers.EventMapper.*;
import static ru.practicum.explorewithme.server.utils.ServerUtil.makePageable;
import static ru.practicum.explorewithme.server.utils.mappers.RequestMapper.toRequestDto;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
@Transactional
public class PrivateServiceImpl implements PrivateService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final LocRepository locRepository;
    private final RequestRepository requestRepository;
    @Override
    public List<EventShortDto> getEventsByOwnerId(long userId, int from, int size) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        List<Event> events = eventRepository.findAllByInitiator_Id(userId, makePageable(from, size));

        return events.stream().map(event -> toEventShort(event, requestRepository.findConfirmedRequests(event.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto updateEvent(long userId, UpdateEventRequest request) {
        Event event = eventRepository.findByInitiator_IdAndId(userId, request.getEventId())
                .orElseThrow(() -> new EventNotFoundException(request.getEventId()));

        Category category = request.getCategory() != null ? categoryRepository.findById(request.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException(request.getCategory())) : null;

        if (List.of(State.PENDING, State.CANCELED).contains(event.getState())) {
            throw new RequestConditionException("Нельзя менять опубликованные события");
        }

        event = makeUpdatableModel(event, request, category);

        return toEventFull(eventRepository.save(event));
    }

    @Override
    public EventFullDto addEvent(long userId, NewEventDto eventDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Category category = categoryRepository.findById(eventDto.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException(eventDto.getCategory()));
        Location location = eventDto.getLocation();

        //интересно почему не работает через orElseThrow
        Optional<Loc> loc = locRepository.findByLatitudeAndLongitude(location.getLat(), location.getLon());
        if (loc.isEmpty()) {
            loc = Optional.of(locRepository.save(new Loc(location)));
        }

        Event event = EventMapper.toEvent(eventDto, category, user, loc.get());

        return EventMapper.toEventFull(eventRepository.save(event));
    }

    @Override
    public EventFullDto getEventByOwnerIdAndEventId(long userId, long eventId) {
        return null;
    }

    @Override
    public EventFullDto cancelEventByOwner(long userId, long eventId) {
        return null;
    }

    @Override
    public List<ParticipationRequestDto> getEventRequests(long userId, long eventId) {
        return null;
    }

    @Override
    public ParticipationRequestDto confirmRequestForEvent(long userId, long eventId, long reqId) {
        return null;
    }

    @Override
    public ParticipationRequestDto rejectRequestForEvent(long userId, long eventId, long reqId) {
        return null;
    }

    @Override
    public List<ParticipationRequestDto> getEventRequestsByUser(long userId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        return requestRepository.findAllByRequestor_Id(userId).stream().map(RequestMapper::toRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto addNewRequestByUser(long userId, long eventId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        Event event = eventRepository.findByIdAndState(eventId, State.PUBLISHED)
                .orElseThrow(() -> new EventNotFoundException(eventId));

        if (event.getInitiator().getId() == userId){
            throw new RequestConditionException("Пользователь не может подать заявку на участие в своем событии");
        }

        if (event.getEventDate().isBefore(LocalDateTime.now())) {
            throw new RequestConditionException("Событие уже прошло");

        }

        requestRepository.findByRequestor_IdAndEvent_Id(userId, eventId).ifPresent((request) ->
            new RequestConditionException(String.format("Запрос на участие в событии %s уже отправлен", event.getTitle())));

        if (event.getParticipantLimit() <= requestRepository.findConfirmedRequests(eventId)) {
            throw new RequestConditionException("Лимит участников события достигнут");
        }

        Request request = event.isModeration() ? Request.makePending(user, event) : Request.makeConfirmed(user, event);

        return toRequestDto(requestRepository.save(request));
    }

    @Override
    public ParticipationRequestDto cancelUserRequest(long userId, long requestId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Request request = requestRepository.findById(requestId).orElseThrow(() -> new RequestNotFoundException(requestId));
        request.setStatus(RequestStatus.REJECTED);
        return toRequestDto(requestRepository.save(request));
    }
}
