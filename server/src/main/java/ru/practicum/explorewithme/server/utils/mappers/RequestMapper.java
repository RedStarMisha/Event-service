package ru.practicum.explorewithme.server.utils.mappers;

import ru.practicum.explorewithme.models.request.ParticipationRequestDto;
import ru.practicum.explorewithme.server.models.Request;

public class RequestMapper {
    public static ParticipationRequestDto toRequestDto(Request request) {
        return new ParticipationRequestDto(request.getCreated(), request.getEvent().getId(), request.getId(),
                request.getRequestor().getId(), request.getStatus());
    }
}