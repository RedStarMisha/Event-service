package ru.practicum.explorewithme.server.services.admin;

import ru.practicum.explorewithme.models.event.AdminUpdateEventRequest;
import ru.practicum.explorewithme.models.event.EventFullDto;
import ru.practicum.explorewithme.server.utils.SelectionConditionForAdmin;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EventService {
    List<EventFullDto> getEvents(SelectionConditionForAdmin condition, HttpServletRequest request);

    EventFullDto updateEvent(long eventId, AdminUpdateEventRequest updateEventRequest);

    EventFullDto publishEvent(long eventId);

    EventFullDto rejectEvent(long eventId);
}
