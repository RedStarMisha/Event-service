package ru.practicum.explorewithme.server.services.admin;

import ru.practicum.explorewithme.models.compilation.CompilationDto;
import ru.practicum.explorewithme.models.compilation.NewCompilationDto;

public interface CompilationService {
    CompilationDto addCompilation(NewCompilationDto compilationDto);

    void deleteCompilation(long compId);

    void deleteEventFromCompilation(long compId, long eventId);

    void addEventToCompilation(long compId, long eventId);

    void unpinCompilation(long compId);

    void pinCompilation(long compId);
}
