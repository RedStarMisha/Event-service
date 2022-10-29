package ru.practicum.explorewithme.server.utils.mappers;

import ru.practicum.explorewithme.models.compilation.CompilationDto;
import ru.practicum.explorewithme.models.compilation.NewCompilationDto;
import ru.practicum.explorewithme.models.event.EventShortDto;
import ru.practicum.explorewithme.server.models.Compilation;
import ru.practicum.explorewithme.server.models.Event;

import java.util.Set;
import java.util.stream.Collectors;

public class CompilationsMapper {

    public static Compilation toCompilation(NewCompilationDto dto, Set<Event> events) {
        return new Compilation(dto.getTitle(), dto.getPinned(), events);
    }

    public static CompilationDto toCompilationDto(Compilation compilation) {
        return new CompilationDto(
                compilation.getEvents().stream().map(EventMapper::toEventShort).collect(Collectors.toSet()),
                compilation.getId(),
                compilation.isPinned(),
                compilation.getTitle()
        );
    }

}