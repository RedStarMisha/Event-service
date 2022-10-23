package ru.practicum.explorewithme.server.priv;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.models.ParticipationRequestDto;
import ru.practicum.explorewithme.models.event.*;
import ru.practicum.explorewithme.server.EventMapper;
import ru.practicum.explorewithme.server.admin.category.Category;
import ru.practicum.explorewithme.server.admin.event.Event;
import ru.practicum.explorewithme.server.admin.event.Loc;
import ru.practicum.explorewithme.server.admin.user.User;
import ru.practicum.explorewithme.server.exceptions.CategoryNotFoundException;
import ru.practicum.explorewithme.server.exceptions.EventNotFoundException;
import ru.practicum.explorewithme.server.exceptions.UserNotFoundException;
import ru.practicum.explorewithme.server.repositories.CategoryRepository;
import ru.practicum.explorewithme.server.repositories.EventRepository;
import ru.practicum.explorewithme.server.repositories.LocRepository;
import ru.practicum.explorewithme.server.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

import static ru.practicum.explorewithme.server.EventMapper.makeUpdatableModel;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
@Transactional
public class PrivateServiceImpl implements PrivateService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final LocRepository locRepository;
    @Override
    public List<EventShortDto> getEventsByOwnerId(long userId, int from, int size) {
        return null;
    }

    @Override
    public EventFullDto updateEvent(long userId, UpdateEventRequest request) {
        Event event = eventRepository.findByInitiator_IdAndId(userId, request.getEventId())
                .orElseThrow(() -> new EventNotFoundException(request.getEventId()));

        Category category = request.getCategory() != null ? categoryRepository.findById(request.getCategory()).orElseThrow(() -> new CategoryNotFoundException(request.getCategory())) : null;
        if (request.getCategory() != null) {

        }
        event = makeUpdatableModel(event, request);
        return null;
    }

    @Override
    public EventFullDto addEvent(long userId, NewEventDto eventDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Category category = categoryRepository.findById(eventDto.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException(eventDto.getCategory()));
        Location location = eventDto.getLocation();

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
        return null;
    }

    @Override
    public ParticipationRequestDto addNewRequestByUser(long userId, long eventId) {
        return null;
    }

    @Override
    public ParticipationRequestDto cancelUserRequest(long userId, long requestId) {
        return null;
    }
}
