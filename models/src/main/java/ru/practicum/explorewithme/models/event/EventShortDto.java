package ru.practicum.explorewithme.models.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.models.category.CategoryDto;
import ru.practicum.explorewithme.models.user.UserShortDto;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class EventShortDto implements Comparable<EventShortDto> {
    private long id;

    private String annotation;

    private CategoryDto category;

    private long confirmedRequests;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    private UserShortDto initiator;

    private boolean paid;

    private String title;

    private long views;

    public EventShortDto(long id, String annotation, CategoryDto category, long confirmedRequests,
                         LocalDateTime eventDate, UserShortDto initiator, boolean paid, String title) {
        this.id = id;
        this.annotation = annotation;
        this.category = category;
        this.confirmedRequests = confirmedRequests;
        this.eventDate = eventDate;
        this.initiator = initiator;
        this.paid = paid;
        this.title = title;
    }

    @Override
    public int compareTo(EventShortDto o) {
        return Long.compare(this.getViews(), o.getViews());
    }
}
