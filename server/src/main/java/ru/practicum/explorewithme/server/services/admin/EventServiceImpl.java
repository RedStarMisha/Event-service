package ru.practicum.explorewithme.server.services.admin;

import ru.practicum.explorewithme.models.event.AdminUpdateEventRequest;
import ru.practicum.explorewithme.models.event.EventFullDto;
import ru.practicum.explorewithme.server.utils.SelectionCondition;

import java.util.List;

public class EventServiceImpl implements EventService {
    @Override
    public List<EventFullDto> getEvents(SelectionCondition condition) {
        return null;
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
