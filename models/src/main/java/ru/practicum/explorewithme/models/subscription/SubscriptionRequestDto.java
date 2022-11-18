package ru.practicum.explorewithme.models.subscription;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.explorewithme.models.user.UserShortDto;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SubscriptionRequestDto {

    private long id;

    private boolean friendship;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    private SubscriptionStatus status;

    private UserShortDto follower;

    private UserShortDto publisher;
}
