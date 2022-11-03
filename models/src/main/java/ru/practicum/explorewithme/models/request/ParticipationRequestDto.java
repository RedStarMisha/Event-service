package ru.practicum.explorewithme.models.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Value;
import ru.practicum.explorewithme.models.user.UserShortDto;

import java.time.LocalDateTime;

@Value
@AllArgsConstructor
public class ParticipationRequestDto {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime created;

    long event;

    long id;

    long requester;

    RequestStatus status;
}
