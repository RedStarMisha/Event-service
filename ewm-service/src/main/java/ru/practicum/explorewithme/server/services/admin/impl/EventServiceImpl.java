package ru.practicum.explorewithme.server.services.admin.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.models.event.AdminUpdateEventRequest;
import ru.practicum.explorewithme.models.event.EventFullDto;
import ru.practicum.explorewithme.models.event.State;
import ru.practicum.explorewithme.server.exceptions.notfound.CategoryNotFoundException;
import ru.practicum.explorewithme.server.exceptions.notfound.EventNotFoundException;
import ru.practicum.explorewithme.server.exceptions.requestcondition.RequestConditionException;
import ru.practicum.explorewithme.server.models.Category;
import ru.practicum.explorewithme.server.models.Event;
import ru.practicum.explorewithme.server.models.QEvent;
import ru.practicum.explorewithme.server.repositories.CategoryRepository;
import ru.practicum.explorewithme.server.repositories.EventRepository;
import ru.practicum.explorewithme.server.services.StatsHandler;
import ru.practicum.explorewithme.server.services.admin.EventService;
import ru.practicum.explorewithme.server.utils.mappers.MyMapper;
import ru.practicum.explorewithme.server.utils.selectioncondition.SearchParam;
import ru.practicum.explorewithme.server.utils.selectioncondition.SelectionConditionForAdmin;

import javax.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.explorewithme.server.utils.mappers.EventMapper.makeUpdatableModelAdmin;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class EventServiceImpl implements EventService {

    private final StatsHandler statsHandler;
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;

    private final MyMapper mapper;

    @Override
    public List<EventFullDto> getEvents(SelectionConditionForAdmin condition, HttpServletRequest request) {
        log.info("Запрошены Events с параметрами поиска {}", condition);
        QEvent qEvent = QEvent.event;
        SearchParam param = condition.getSearchParameters(qEvent);

        return eventRepository.findAll(param.getBooleanExpression(), param.getPageable()).stream()
                .peek(statsHandler::statsHandle).map(mapper::toEventFull)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto updateEvent(long eventId, AdminUpdateEventRequest updateEventRequest) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

        if (updateEventRequest.getCategory() != null) {
            Category category = categoryRepository.findById(updateEventRequest.getCategory()).orElseThrow(() ->
                    new CategoryNotFoundException(updateEventRequest.getCategory()));
            event.setCategory(category);
        }

        makeUpdatableModelAdmin(event, updateEventRequest);

        event = eventRepository.save(event);
        log.info("Event с id={} обновлен. Новые параметры {}", eventId, updateEventRequest);

        return mapper.toEventFull(statsHandler.statsHandle(event));
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

        return mapper.toEventFull(event);
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

        return mapper.toEventFull(statsHandler.statsHandle(event));
    }
}
