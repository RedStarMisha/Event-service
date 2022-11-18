package ru.practicum.explorewithme.server.utils.mappers;

import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.models.request.ParticipationRequestDto;
import ru.practicum.explorewithme.models.request.ParticipationRequestForSubscription;
import ru.practicum.explorewithme.server.models.Group;
import ru.practicum.explorewithme.server.models.Request;

import java.util.stream.Collectors;

@Component
public class RequestMapper {
    public static ParticipationRequestDto toRequestDto(Request request) {
        return new ParticipationRequestDto(request.getCreated(), request.getEvent().getId(), request.getId(),
                request.getRequestor().getId(), request.getStatus());
    }

    public static ParticipationRequestForSubscription toRequestForSubscription(Request request) {
        return new ParticipationRequestForSubscription(
                request.getId(),
                request.getCreated(),
                request.getEvent().getId(),
                request.getRequestor().getId(),
                request.getStatus(),
                request.getGroups().stream().map(Group::getTitle).collect(Collectors.toSet()));
    }
}
