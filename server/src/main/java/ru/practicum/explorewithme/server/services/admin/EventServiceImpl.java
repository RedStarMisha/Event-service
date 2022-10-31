package ru.practicum.explorewithme.server.services.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import ru.practicum.explorewithme.clients.stat.StatClient;
import ru.practicum.explorewithme.models.event.AdminUpdateEventRequest;
import ru.practicum.explorewithme.models.event.EventFullDto;
import ru.practicum.explorewithme.models.event.Location;
import ru.practicum.explorewithme.models.event.State;
import ru.practicum.explorewithme.models.statistics.EndpointHit;
import ru.practicum.explorewithme.models.statistics.ViewStats;
import ru.practicum.explorewithme.server.exceptions.notfound.CategoryNotFoundException;
import ru.practicum.explorewithme.server.exceptions.notfound.EventNotFoundException;
import ru.practicum.explorewithme.server.exceptions.requestcondition.RequestConditionException;
import ru.practicum.explorewithme.server.models.Category;
import ru.practicum.explorewithme.server.models.Event;
import ru.practicum.explorewithme.server.models.Loc;
import ru.practicum.explorewithme.server.models.QEvent;
import ru.practicum.explorewithme.server.repositories.CategoryRepository;
import ru.practicum.explorewithme.server.repositories.EventRepository;
import ru.practicum.explorewithme.server.repositories.LocRepository;
import ru.practicum.explorewithme.server.services.StatsHandler;
import ru.practicum.explorewithme.server.utils.SearchParam;
import ru.practicum.explorewithme.server.utils.SelectionConditionForAdmin;
import ru.practicum.explorewithme.server.utils.mappers.EventMapper;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.explorewithme.server.utils.mappers.EventMapper.makeUpdatableModelAdmin;
import static ru.practicum.explorewithme.server.utils.mappers.EventMapper.toEventFull;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
@Transactional
public class EventServiceImpl implements EventService {

    private final StatsHandler statsHandler;
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final LocRepository locRepository;
    @Override
    public List<EventFullDto> getEvents(SelectionConditionForAdmin condition, HttpServletRequest request) {
        log.info("Запрошены Events с параметрами поиска {}", condition);
        QEvent qEvent = QEvent.event;
        SearchParam param = condition.getSearchParameters(qEvent);

        return eventRepository.findAll(param.getBooleanExpression(), param.getPageable()).stream()
                .peek(event -> statsHandler.statsHandle(event)).map(EventMapper::toEventFull)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto updateEvent(long eventId, AdminUpdateEventRequest updateEventRequest) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

        log.info("Event с id={} будет обновлен. Новые параметры {}", eventId, updateEventRequest);

        if (updateEventRequest.getCategory() != null) {
            Category category = categoryRepository.findById(updateEventRequest.getCategory()).orElseThrow(() ->
                    new CategoryNotFoundException(updateEventRequest.getCategory()));
            event.setCategory(category);
        }

        event = makeUpdatableModelAdmin(event, updateEventRequest);

        Loc location = event.getLocation();

        locRepository.findByLatitudeAndLongitude(location.getLatitude(), location.getLongitude())
                .orElseGet(() -> locRepository.save(location));

        event = eventRepository.save(event);

        return toEventFull(statsHandler.statsHandle(event));
    }

    @Override
    public EventFullDto publishEvent(long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

        if (event.getState() != State.PENDING) {
            throw new RequestConditionException("Можно публиковать события только со статусом на рассмотрении");
        }
        if (event.getEventDate().minusHours(1).isBefore(LocalDateTime.now())) {
            throw new RequestConditionException("Слишком поздно для публикации события");
        }

        event.setState(State.PUBLISHED);
        event.setPublished(LocalDateTime.now());

        event = eventRepository.save(event);

        log.info("Event с id={} опубликован {}", eventId, event.getPublished());

        return toEventFull(statsHandler.statsHandle(event));
    }

    @Override
    public EventFullDto rejectEvent(long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

        if (event.getState() != State.PENDING) {
            throw new RequestConditionException("Отменить можно только события находящиеся на рассмотрении");
        }

        event.setState(State.CANCELED);

        event = eventRepository.save(event);

        log.info("Event с id={} отменен", eventId);

        return toEventFull(statsHandler.statsHandle(event));
    }


}
