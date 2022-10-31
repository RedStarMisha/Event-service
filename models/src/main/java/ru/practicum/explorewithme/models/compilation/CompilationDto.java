package ru.practicum.explorewithme.models.compilation;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.explorewithme.models.event.EventShortDto;

import java.util.List;

@Data
@AllArgsConstructor
public class CompilationDto {

    private long id;

    private String title;

    private boolean pinned;

    private List<EventShortDto> events;
}
