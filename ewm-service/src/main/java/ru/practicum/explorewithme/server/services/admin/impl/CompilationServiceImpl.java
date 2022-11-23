package ru.practicum.explorewithme.server.services.admin.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.models.compilation.CompilationDto;
import ru.practicum.explorewithme.models.compilation.NewCompilationDto;
import ru.practicum.explorewithme.server.exceptions.notfound.CompilationNotFoundException;
import ru.practicum.explorewithme.server.exceptions.notfound.EventNotFoundException;
import ru.practicum.explorewithme.server.exceptions.requestcondition.RequestConditionException;
import ru.practicum.explorewithme.server.models.Compilation;
import ru.practicum.explorewithme.server.models.Event;
import ru.practicum.explorewithme.server.repositories.CompilationRepository;
import ru.practicum.explorewithme.server.repositories.EventRepository;
import ru.practicum.explorewithme.server.services.admin.CompilationService;
import ru.practicum.explorewithme.server.utils.mappers.MyMapper;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.explorewithme.server.utils.mappers.CompilationsMapper.toCompilation;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    private final MyMapper mapper;


    @Override
    public CompilationDto addCompilation(NewCompilationDto compilationDto) {
        List<Event> events = Arrays.stream(compilationDto.getEvents()).mapToObj(id -> eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id))).collect(Collectors.toList());

        Compilation compilation = compilationRepository.save(toCompilation(compilationDto, events));
        log.info("Compilation {} c id={} добавлена", compilation.getTitle(), compilation.getId());

        return mapper.toCompilationDto(compilation);
    }

    @Override
    public void deleteCompilation(long compId) {
        compilationRepository.findById(compId).orElseThrow(() -> new CompilationNotFoundException(compId));
        compilationRepository.deleteById(compId);
        log.info("Compilation с id={} удалена", compId);
    }

    @Override
    public void deleteEventFromCompilation(long compId, long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

        Compilation compilation = compilationRepository.findById(compId).orElseThrow(() ->
                new CompilationNotFoundException(compId));

        if (!compilation.getEvents().contains(event)) {
            throw new RequestConditionException("Event с id = " + eventId + " отсутсвует в Compilation с id = " + compId);
        }

        compilation.deleteEvent(event);

        compilationRepository.save(compilation);

        log.info("Event с id={} удалено из Compilation с id={}", eventId, compId);
    }

    @Override
    public void addEventToCompilation(long compId, long eventId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(() -> new CompilationNotFoundException(compId));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

        if (compilation.getEvents().contains(event)) {
            throw new RequestConditionException("Event с id = " + eventId + " уже в Compilation с id = " + compId);
        }

        compilation.addEvent(event);

        compilationRepository.save(compilation);
        log.info("Event с id={} добавлено в Compilation с id={}", eventId, compId);
    }

    @Override
    public void unpinCompilation(long compId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(() ->
                new CompilationNotFoundException(compId));

        if (!compilation.isPinned()) {
            throw new RequestConditionException("Compilation с id = " + compId + " уже откреплена от главной страницы ");
        }

        compilation.setPinned(false);

        compilationRepository.save(compilation);
        log.info("Compilation с id={} снята с главной страницы", compId);
    }

    @Override
    public void pinCompilation(long compId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(() ->
                new CompilationNotFoundException(compId));

        if (compilation.isPinned()) {
            throw new RequestConditionException("Compilation с id = " + compId + " уже на главной странице ");
        }

        compilation.setPinned(true);

        compilationRepository.save(compilation);
        log.info("Compilation с id={} размещена на главной странице", compId);
    }
}
