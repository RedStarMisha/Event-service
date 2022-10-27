package ru.practicum.explorewithme.server.services.priv;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.models.event.State;
import ru.practicum.explorewithme.models.event.*;
import ru.practicum.explorewithme.models.request.ParticipationRequestDto;
import ru.practicum.explorewithme.models.request.RequestStatus;
import ru.practicum.explorewithme.server.exceptions.notfound.RequestNotFoundException;
import ru.practicum.explorewithme.server.models.*;
import ru.practicum.explorewithme.server.exceptions.notfound.CategoryNotFoundException;
import ru.practicum.explorewithme.server.exceptions.notfound.EventNotFoundException;
import ru.practicum.explorewithme.server.exceptions.notfound.UserNotFoundException;
import ru.practicum.explorewithme.server.exceptions.requestcondition.RequestConditionException;
import ru.practicum.explorewithme.server.repositories.*;
import ru.practicum.explorewithme.server.utils.mappers.RequestMapper;

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

        return events.stream().map(event -> toEventShort(event)).collect(Collectors.toList());
    }

    @Override
    public EventFullDto updateEvent(long userId, UpdateEventRequest request) {
        Event event = eventRepository.findByInitiator_IdAndId(userId, request.getEventId())
                .orElseThrow(() -> new EventNotFoundException(request.getEventId()));

        Category category = request.getCategory() != null ? categoryRepository.findById(request.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException(request.getCategory())) : null;

        if (!List.of(State.PENDING, State.CANCELED).contains(event.getState())) {
            throw new RequestConditionException("Нельзя менять опубликованные события");
        }

        event = makeUpdatableModelPrivate(event, request, category);

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

        Event event = toEvent(eventDto, category, user, loc.get());

        return toEventFull(eventRepository.save(event));
    }

    @Override
    public EventFullDto getEventByOwnerIdAndEventId(long userId, long eventId) {
        Event event = eventRepository.findByInitiator_IdAndId(userId, eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));

        return toEventFull(event);
    }

    @Override
    public EventFullDto cancelEventByOwner(long userId, long eventId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Event event = eventRepository.findByInitiator_IdAndId(userId, eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));

        switch (event.getState()) {
            case PUBLISHED:
                throw new RequestConditionException("Нельзя отменить опубликованное событие");
            case CANCELED:
                throw new RequestConditionException("Событие уже отменено");
            default:
                event.setState(State.CANCELED);
                return toEventFull(event);
        }
    }
    @Override
    public List<ParticipationRequestDto> getEventRequests(long userId, long eventId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        eventRepository.findByInitiator_IdAndId(userId, eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));

        return requestRepository.findAllByEvent_Id(eventId).stream().map(RequestMapper::toRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto confirmRequestForEvent(long userId, long eventId, long reqId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Event event = eventRepository.findByInitiator_IdAndId(userId, eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));
        Request request = requestRepository.findById(reqId).orElseThrow(() -> new RequestNotFoundException(reqId));

        if (event.getParticipantLimit() == event.getNumberConfirmed()) {
            requestRepository.rejectedAllRequestsByEventId(eventId);
            throw new RequestConditionException("Лимит участников события достигнут");
        }
        eventRepository.addConfirmedRequest(eventId);

        request.setStatus(RequestStatus.CONFIRMED);

        return toRequestDto(request);
    }

    @Override
    public ParticipationRequestDto rejectRequestForEvent(long userId, long eventId, long reqId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        eventRepository.findByInitiator_IdAndId(userId, eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));
        Request request = requestRepository.findById(reqId).orElseThrow(() -> new RequestNotFoundException(reqId));

        request.setStatus(RequestStatus.REJECTED);

        return toRequestDto(request);
    }

}
