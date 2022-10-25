package ru.practicum.explorewithme.models.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.explorewithme.models.category.CategoryDto;
import ru.practicum.explorewithme.models.user.UserShortDto;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class EventFullDto {
    private String annotation;

    private CategoryDto category;

    private long confirmedRequests;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    private long id;

    private UserShortDto initiator;

    private Location location;

    private boolean paid;

    private int participantLimit;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedOn;

    private boolean requestModeration;

    private State state;

    private String title;

    private long views;
}
