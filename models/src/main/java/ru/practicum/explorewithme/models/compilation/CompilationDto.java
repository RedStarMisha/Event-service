package ru.practicum.explorewithme.models.compilation;

import lombok.Data;
import ru.practicum.explorewithme.models.event.EventShortDto;

@Data
public class CompilationDto {

    EventShortDto events;

    long id;

    boolean pinned;

    String title;
}
