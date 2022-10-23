package ru.practicum.explorewithme.server;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.practicum.explorewithme.models.State;
import ru.practicum.explorewithme.models.event.EventFullDto;
import ru.practicum.explorewithme.models.event.Location;
import ru.practicum.explorewithme.models.event.NewEventDto;
import ru.practicum.explorewithme.models.event.UpdateEventRequest;
import ru.practicum.explorewithme.server.admin.category.Category;
import ru.practicum.explorewithme.server.admin.category.CategoryMapper;
import ru.practicum.explorewithme.server.admin.event.Event;
import ru.practicum.explorewithme.server.admin.event.Loc;
import ru.practicum.explorewithme.server.admin.user.User;
import ru.practicum.explorewithme.server.admin.user.UserMapper;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static ru.practicum.explorewithme.server.admin.category.CategoryMapper.toDto;
import static ru.practicum.explorewithme.server.admin.user.UserMapper.toUserShort;

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
        return event;
    }

    public static EventFullDto toEventFull(Event event) {
        Location location = new Location(event.getLocation().getLatitude(), event.getLocation().getLongitude());
        return new EventFullDto(event.getAnnotation(), toDto(event.getCategory()), 0, event.getCreated(),
                event.getDescription(), event.getEventDate(), event.getId(), toUserShort(event.getInitiator()), location,
                event.isPaid(), event.getParticipantLimit(), event.getPublished(), event.isModeration(), event.getState(),
                event.getTitle(), 0);
    }

    public static Event makeUpdatableModel(Event event, UpdateEventRequest request) {
        if (request.getAnnotation() != null) {
            event.setAnnotation(request.getAnnotation());
        }
        if ()
    }


    private Long category;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    @NotNull
    private long eventId;

    private boolean paid;

    private int participantLimit;

    private String title;
}
