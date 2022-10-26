package ru.practicum.explorewithme.server.services.admin;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.models.event.AdminUpdateEventRequest;
import ru.practicum.explorewithme.models.event.EventFullDto;
import ru.practicum.explorewithme.server.models.QEvent;
import ru.practicum.explorewithme.server.repositories.EventRepository;
import ru.practicum.explorewithme.server.utils.SelectionConditionForAdmin;
import ru.practicum.explorewithme.server.utils.mappers.EventMapper;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.explorewithme.server.utils.mappers.EventMapper.toEventFull;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    @Override
    public List<EventFullDto> getEvents(SelectionConditionForAdmin condition) {
        log.info("Запрошены Events с параметрами поиска {}", condition);
        QEvent qEvent = QEvent.event;
        SelectionConditionForAdmin.SearchParam param = condition.getSearchParameters(qEvent);
        return eventRepository.findByParam(param.getBooleanExpression(), param.getPageable()).stream()
                .map(EventMapper::toEventFull).collect(Collectors.toList());
    }

    @Override
    public EventFullDto updateEvent(long eventId, AdminUpdateEventRequest updateEventRequest) {
        return null;
    }

    @Override
    public EventFullDto publishEvent(long eventId) {
        return null;
    }

    @Override
    public EventFullDto rejectEvent(long eventId) {
        return null;
    }
}
