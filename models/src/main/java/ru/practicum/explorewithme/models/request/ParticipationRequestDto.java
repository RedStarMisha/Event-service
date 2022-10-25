package ru.practicum.explorewithme.models.request;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@AllArgsConstructor
public class ParticipationRequestDto {

    LocalDateTime created;

    long event;

    long id;

    long requester;

    RequestStatus requestStatus;
}
