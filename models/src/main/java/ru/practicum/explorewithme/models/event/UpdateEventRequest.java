package ru.practicum.explorewithme.models.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.practicum.explorewithme.models.validation.CheckEventDate;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@CheckEventDate
public class UpdateEventRequest implements EventDateCheckable {

    private String annotation;

    private Long category;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    @NotNull
    private Long eventId;

    private Boolean paid;

    private Integer participantLimit;

    private String title;
}
