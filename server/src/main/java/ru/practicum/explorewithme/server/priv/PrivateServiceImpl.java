package ru.practicum.explorewithme.server.priv;

import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.models.ParticipationRequestDto;
import ru.practicum.explorewithme.models.event.EventFullDto;
import ru.practicum.explorewithme.models.event.EventShortDto;
import ru.practicum.explorewithme.models.event.NewEventDto;
import ru.practicum.explorewithme.models.event.UpdateEventRequest;

import java.util.List;

@Service
public class PrivateServiceImpl implements PrivateService {
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
        return null;
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
