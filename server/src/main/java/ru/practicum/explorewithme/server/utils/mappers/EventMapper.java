package ru.practicum.explorewithme.server.utils.mappers;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.practicum.explorewithme.models.event.State;
import ru.practicum.explorewithme.models.event.*;
import ru.practicum.explorewithme.server.models.Category;
import ru.practicum.explorewithme.server.models.Event;
import ru.practicum.explorewithme.server.models.Loc;
import ru.practicum.explorewithme.server.models.User;

import java.time.LocalDateTime;
import java.util.Optional;

import static ru.practicum.explorewithme.server.utils.mappers.CategoryMapper.toDto;
import static ru.practicum.explorewithme.server.utils.mappers.UserMapper.toUserShort;

public class EventMapper {
    public static Event toEvent(NewEventDto newEventDto, Category category, User initializer, Loc location) {
        Event event = new Event();
        event.setAnnotation(newEventDto.getAnnotation());
        event.setDescription(newEventDto.getDescription());
        event.setTitle(newEventDto.getTitle());
        event.setEventDate(newEventDto.getEventDate());
        event.setCategory(category);
        event.setInitiator(initializer);
        event.setLocation(location);
        event.setModeration(newEventDto.isRequestModeration());
        event.setPaid(newEventDto.isPaid());
        event.setCreated(LocalDateTime.now());
        event.setState(State.PENDING);
        event.setParticipantLimit(newEventDto.getParticipantLimit());
        return event;
    }

    public static EventFullDto toEventFull(Event event) {
        Location location = new Location(event.getLocation().getLatitude(), event.getLocation().getLongitude());
        return new EventFullDto(
                event.getAnnotation(),
                toDto(event.getCategory()),
                event.getNumberConfirmed(),
                event.getCreated(),
                event.getDescription(),
                event.getEventDate(),
                event.getId(),
                toUserShort(event.getInitiator()),
                location,
                event.isPaid(),
                event.getParticipantLimit(),
                event.getPublished(),
                event.isModeration(),
                event.getState(),
                event.getTitle(),
                event.getViews());
    }

    public static Event makeUpdatableModelPrivate(Event event, UpdateEventRequest request, Category category) {
        Optional.ofNullable(request.getAnnotation()).ifPresent(event::setAnnotation);
        Optional.ofNullable(category).ifPresent(event::setCategory);
        Optional.ofNullable(request.getDescription()).ifPresent(event::setDescription);
        Optional.ofNullable(request.getEventDate()).ifPresent(event::setEventDate);
        Optional.ofNullable(request.getPaid()).ifPresent(event::setPaid);
        Optional.ofNullable(request.getParticipantLimit()).ifPresent(event::setParticipantLimit);
        Optional.ofNullable(request.getTitle()).ifPresent(event::setTitle);
        event.setState(State.PENDING);
        return event;
    }

    public static Event makeUpdatableModelAdmin(Event event, AdminUpdateEventRequest request) {
        Optional.ofNullable(request.getAnnotation()).ifPresent(event::setAnnotation);
        Optional.ofNullable(request.getDescription()).ifPresent(event::setDescription);
        Optional.ofNullable(request.getEventDate()).filter(date -> LocalDateTime.now().isBefore(date))
                .ifPresent(event::setEventDate);
        Optional.ofNullable(request.getPaid()).ifPresent(event::setPaid);
        Optional.ofNullable(request.getParticipantLimit()).filter(limit -> limit > event.getNumberConfirmed() || limit == 0)
                .ifPresent(event::setParticipantLimit);
        Optional.ofNullable(request.getTitle()).ifPresent(event::setTitle);
        Optional.ofNullable(request.getRequestModeration()).ifPresent(event::setModeration);
        return event;
    }

    public static EventShortDto toEventShort(Event event) {
        EventShortDto eventShort = new EventShortDto();
        eventShort.setId(event.getId());
        eventShort.setAnnotation(event.getAnnotation());
        eventShort.setCategory(toDto(event.getCategory()));
        eventShort.setConfirmedRequests(event.getNumberConfirmed());
        eventShort.setEventDate(event.getEventDate());
        eventShort.setInitiator(toUserShort(event.getInitiator()));
        eventShort.setPaid(event.isPaid());
        eventShort.setTitle(event.getTitle());
        eventShort.setViews(event.getViews());
        return eventShort;
    }
}
