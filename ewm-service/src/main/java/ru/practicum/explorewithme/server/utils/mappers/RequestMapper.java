package ru.practicum.explorewithme.server.utils.mappers;

import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.models.request.ParticipationRequestDto;
import ru.practicum.explorewithme.server.models.Request;

@Component
public class RequestMapper {
    public static ParticipationRequestDto toRequestDto(Request request) {
        return new ParticipationRequestDto(request.getCreated(), request.getEvent().getId(), request.getId(),
                request.getRequestor().getId(), request.getStatus());
    }
}
