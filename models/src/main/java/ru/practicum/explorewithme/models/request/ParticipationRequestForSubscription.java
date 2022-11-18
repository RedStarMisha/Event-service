package ru.practicum.explorewithme.models.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Set;

@Value
@AllArgsConstructor
public class ParticipationRequestForSubscription {

    long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime created;

    long event;

    long requester;

    RequestStatus status;

    Set<String> groupName;
}
