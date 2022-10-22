package ru.practicum.explorewithme.models;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ParticipationRequestDto {

    LocalDateTime created;

    long event;

    long id;

    long requester;

    RequestStatus requestStatus;
}
