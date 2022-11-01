package ru.practicum.explorewithme.server.utils.mappers;

import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.models.compilation.CompilationDto;
import ru.practicum.explorewithme.models.compilation.NewCompilationDto;
import ru.practicum.explorewithme.server.models.Compilation;
import ru.practicum.explorewithme.server.models.Event;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompilationsMapper {

    public static Compilation toCompilation(NewCompilationDto dto, List<Event> events) {
        return new Compilation(dto.getTitle(), dto.getPinned(), events);
    }

    public static CompilationDto toCompilationDto(Compilation compilation) {
        return new CompilationDto(
                compilation.getId(),
                compilation.getTitle(),
                compilation.isPinned(),
                compilation.getEvents().stream().map(EventMapper::toEventShort).collect(Collectors.toList())
        );
    }

}
