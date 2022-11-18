package ru.practicum.explorewithme.models.subscription;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FollowerDto {
    private long id;

    private String group;

    private LocalDateTime added;

    private long followerId;

    private long requestId;
}
