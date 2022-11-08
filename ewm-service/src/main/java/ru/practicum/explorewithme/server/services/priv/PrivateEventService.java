package ru.practicum.explorewithme.server.services.priv;

import ru.practicum.explorewithme.models.event.*;
import ru.practicum.explorewithme.models.request.ParticipationRequestDto;
import ru.practicum.explorewithme.server.utils.selectioncondition.SelectionConditionForPrivate;

import java.util.List;

public interface PrivateEventService {

    List<EventShortDto> getEventsByOwnerId(long userId, int from, int size);

    EventFullDto updateEvent(long userId, UpdateEventRequest request);

    EventFullDto addEvent(long userId, NewEventDto eventDto);

    EventFullDto getEventByOwnerIdAndEventId(long userId, long eventId);

    EventFullDto cancelEventByOwner(long userId, long eventId);

    List<ParticipationRequestDto> getEventRequests(long userId, long eventId);

    ParticipationRequestDto confirmRequestForEvent(long userId, long eventId, long reqId);

    ParticipationRequestDto rejectRequestForEvent(long userId, long eventId, long reqId);

    List<EventFullDto> getEventsWhereParticipant(long followerId, Long userId, SelectionConditionForPrivate selection);

    List<EventFullDto> getEventsWhereCreator(long followerId, Long userId, SelectionConditionForPrivate selection);
}
