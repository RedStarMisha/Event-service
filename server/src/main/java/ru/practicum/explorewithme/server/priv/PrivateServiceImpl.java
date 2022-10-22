package ru.practicum.explorewithme.server.priv;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.models.ParticipationRequestDto;
import ru.practicum.explorewithme.models.event.EventFullDto;
import ru.practicum.explorewithme.models.event.EventShortDto;
import ru.practicum.explorewithme.models.event.NewEventDto;
import ru.practicum.explorewithme.models.event.UpdateEventRequest;
import ru.practicum.explorewithme.server.EventMapper;
import ru.practicum.explorewithme.server.admin.category.Category;
import ru.practicum.explorewithme.server.admin.event.Event;
import ru.practicum.explorewithme.server.admin.user.User;
import ru.practicum.explorewithme.server.exceptions.CategoryNotFoundException;
import ru.practicum.explorewithme.server.exceptions.UserNotFoundException;
import ru.practicum.explorewithme.server.repositories.CategoryRepository;
import ru.practicum.explorewithme.server.repositories.EventRepository;
import ru.practicum.explorewithme.server.repositories.UserRepository;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
@Transactional
public class PrivateServiceImpl implements PrivateService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;
    @Override
    public List<EventShortDto> getEventsByOwnerId(long userId, int from, int size) {
        return null;
    }

    @Override
    public EventFullDto updateEvent(long userId, UpdateEventRequest request) {
        return null;
    }

    @Override
    public EventFullDto addEvent(long userId, NewEventDto eventDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Category category = categoryRepository.findById(eventDto.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException(eventDto.getCategory()));
        Event event = EventMapper.toEvent(eventDto, category, user);

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
