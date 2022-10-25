package ru.practicum.explorewithme.server.services.admin;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.models.event.State;
import ru.practicum.explorewithme.models.compilation.CompilationDto;
import ru.practicum.explorewithme.models.compilation.NewCompilationDto;
import ru.practicum.explorewithme.models.event.EventShortDto;
import ru.practicum.explorewithme.server.exceptions.notfound.CompilationNotFoundException;
import ru.practicum.explorewithme.server.exceptions.notfound.EventNotFoundException;
import ru.practicum.explorewithme.server.exceptions.requestcondition.RequestConditionException;
import ru.practicum.explorewithme.server.models.Compilation;
import ru.practicum.explorewithme.server.models.Event;
import ru.practicum.explorewithme.server.repositories.CompilationRepository;
import ru.practicum.explorewithme.server.repositories.EventRepository;
import ru.practicum.explorewithme.server.repositories.RequestRepository;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.practicum.explorewithme.server.utils.mappers.CompilationsMapper.toCompilation;
import static ru.practicum.explorewithme.server.utils.mappers.CompilationsMapper.toCompilationDto;
import static ru.practicum.explorewithme.server.utils.mappers.EventMapper.toEventShort;

@Service
@Slf4j
@AllArgsConstructor(onConstructor_ = @Autowired)
@Transactional
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;


    @Override
    public CompilationDto addCompilation(NewCompilationDto compilationDto) {
        Set<Event> events = Arrays.stream(compilationDto.getEvents()).mapToObj(id -> eventRepository.findById(id)
                .filter(event -> event.getState() == State.PUBLISHED)
                    .orElseThrow(() -> new EventNotFoundException(id))).collect(Collectors.toSet());

        Compilation compilation = compilationRepository.save(toCompilation(compilationDto, events));

        Set<EventShortDto> shortEvents = events.stream().map(event ->
                toEventShort(event, requestRepository.findConfirmedRequests(event.getId()))).collect(Collectors.toSet());

        return toCompilationDto(compilation, shortEvents);
    }

    @Override
    public void deleteCompilation(long compId) {
        compilationRepository.findById(compId).orElseThrow(() -> new CompilationNotFoundException(compId));
        compilationRepository.deleteById(compId);
    }

    @Override
    public void deleteEventFromCompilation(long compId, long eventId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(() -> new CompilationNotFoundException(compId));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

        if(!compilation.getEvents().contains(event)) {
            throw new RequestConditionException("Event с id = " + eventId + " отсутсвует в Compilation с id = " + compId);
        }

        compilation.deleteEvent(event);

        compilationRepository.save(compilation);
    }

    @Override
    public void addEventFromCompilation(long compId, long eventId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(() -> new CompilationNotFoundException(compId));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

        if(compilation.getEvents().contains(event)) {
            throw new RequestConditionException("Event с id = " + eventId + " уже в Compilation с id = " + compId);
        }

        compilation.addEvent(event);

        compilationRepository.save(compilation);
    }

    @Override
    public void unpinCompilation(long compId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(() ->
                new CompilationNotFoundException(compId));

        if(!compilation.isPinned()) {
            throw new RequestConditionException("Compilation с id = " + compId + " уже откреплена от главной страницы ");
        }

        compilation.setPinned(false);

        compilationRepository.save(compilation);
    }

    @Override
    public void pinCompilation(long compId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(() ->
                new CompilationNotFoundException(compId));

        if(compilation.isPinned()) {
            throw new RequestConditionException("Compilation с id = " + compId + " уже на главной странице ");
        }

        compilation.setPinned(true);

        compilationRepository.save(compilation);
    }
}
