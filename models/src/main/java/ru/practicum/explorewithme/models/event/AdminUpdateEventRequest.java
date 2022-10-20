package ru.practicum.explorewithme.models.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.practicum.explorewithme.models.Location;
import ru.practicum.explorewithme.models.category.CategoryDto;

import java.time.LocalDateTime;

@Data
public class AdminUpdateEventRequest {

    private String annotation;

    private CategoryDto category;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    private Location location;

    private boolean paid;

    private int participantLimit;

    private boolean requestModeration;

    private String title;
}
