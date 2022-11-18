package ru.practicum.explorewithme.models.subscription;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.explorewithme.models.user.UserWithSubscriptionDto;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FollowerFullDto {
    private long id;

    private String group;

    private LocalDateTime added;

    private UserWithSubscriptionDto followerId;

    private long requestId;
}
