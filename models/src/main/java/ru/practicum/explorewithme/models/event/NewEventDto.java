package ru.practicum.explorewithme.models.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;
import ru.practicum.explorewithme.models.validation.CheckEventDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@CheckEventDate
public class NewEventDto implements EventDateCheckable {
    @NotBlank
    private String annotation;

    @NotNull
    private Long category;

    @NotNull
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull
    private LocalDateTime eventDate;

    @NotNull
    private Location location;

    private boolean paid = false;

    private int participantLimit = 10;

    private boolean requestModeration;

    @NotBlank
    private String title;
}
