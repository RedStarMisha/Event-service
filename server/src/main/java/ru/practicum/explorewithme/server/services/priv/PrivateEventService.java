package ru.practicum.explorewithme.server.services.priv;

import ru.practicum.explorewithme.models.request.ParticipationRequestDto;
import ru.practicum.explorewithme.models.event.EventFullDto;
import ru.practicum.explorewithme.models.event.EventShortDto;
import ru.practicum.explorewithme.models.event.NewEventDto;
import ru.practicum.explorewithme.models.event.UpdateEventRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PrivateEventService {

    List<EventShortDto> getEventsByOwnerId(long userId, int from, int size);

    EventFullDto updateEvent(long userId, UpdateEventRequest request);

    EventFullDto addEvent(long userId, NewEventDto eventDto);

    EventFullDto getEventByOwnerIdAndEventId(long userId, long eventId, HttpServletRequest request);

    EventFullDto cancelEventByOwner(long userId, long eventId);
    List<ParticipationRequestDto> getEventRequests(long userId, long eventId);

    ParticipationRequestDto confirmRequestForEvent(long userId, long eventId, long reqId);

    ParticipationRequestDto rejectRequestForEvent(long userId, long eventId, long reqId);
}
