package ru.practicum.explorewithme.server.services.admin;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.models.compilation.CompilationDto;
import ru.practicum.explorewithme.models.compilation.NewCompilationDto;
import ru.practicum.explorewithme.server.exceptions.notfound.EventNotFoundException;
import ru.practicum.explorewithme.server.models.Compilation;
import ru.practicum.explorewithme.server.models.Event;
import ru.practicum.explorewithme.server.repositories.CompilationRepository;
import ru.practicum.explorewithme.server.repositories.EventRepository;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.practicum.explorewithme.server.utils.mappers.CompilationsMapper.toCompilation;

@Service
@Slf4j
@AllArgsConstructor(onConstructor_ = @Autowired)
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;


    @Override
    public CompilationDto addCompilation(NewCompilationDto compilationDto) {
        Set<Event> events = Arrays.stream(compilationDto.getEvents()).mapToObj(id ->
            eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException(id))).collect(Collectors.toSet());
        Compilation compilation = toCompilation(compilationDto, events);

        return compilationRepository.save(compilation);
    }

    @Override
    public void deleteCompilation(long compId) {

    }

    @Override
    public void deleteEventFromCompilation(long compId, long eventId) {

    }

    @Override
    public void addEventFromCompilation(long compId, long eventId) {

    }

    @Override
    public void unpinCompilation(long compId) {

    }

    @Override
    public void pinCompilation(long compId) {

    }
}
