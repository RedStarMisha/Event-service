package ru.practicum.explorewithme.models.compilation;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.explorewithme.models.event.EventShortDto;

import java.util.Set;

@Data
@AllArgsConstructor
public class CompilationDto {

    private Set<EventShortDto> events;

    private long id;

    private boolean pinned;

    private String title;
}
