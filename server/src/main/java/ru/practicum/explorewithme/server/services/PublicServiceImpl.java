package ru.practicum.explorewithme.server.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ServerErrorException;
import ru.practicum.explorewithme.clients.stat.StatClient;
import ru.practicum.explorewithme.models.category.CategoryDto;
import ru.practicum.explorewithme.models.compilation.CompilationDto;
import ru.practicum.explorewithme.models.event.EventFullDto;
import ru.practicum.explorewithme.models.event.EventShortDto;
import ru.practicum.explorewithme.models.event.State;
import ru.practicum.explorewithme.models.statistics.EndpointHit;
import ru.practicum.explorewithme.models.statistics.ViewStats;
import ru.practicum.explorewithme.server.exceptions.notfound.CategoryNotFoundException;
import ru.practicum.explorewithme.server.exceptions.notfound.CompilationNotFoundException;
import ru.practicum.explorewithme.server.exceptions.notfound.EventNotFoundException;
import ru.practicum.explorewithme.server.exceptions.requestcondition.RequestConditionException;
import ru.practicum.explorewithme.server.models.Compilation;
import ru.practicum.explorewithme.server.models.Event;
import ru.practicum.explorewithme.server.repositories.CategoryRepository;
import ru.practicum.explorewithme.server.repositories.CompilationRepository;
import ru.practicum.explorewithme.server.repositories.EventRepository;
import ru.practicum.explorewithme.server.utils.SelectionConditionForPublic;
import ru.practicum.explorewithme.server.utils.mappers.CategoryMapper;
import ru.practicum.explorewithme.server.utils.mappers.CompilationsMapper;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.rmi.ServerException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.explorewithme.server.utils.ServerUtil.makePageable;
import static ru.practicum.explorewithme.server.utils.mappers.EventMapper.toEventFull;

@Service
@Slf4j
@Transactional
@AllArgsConstructor(onConstructor_ = @Autowired)
public class PublicServiceImpl implements PublicService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final EventRepository eventRepository;
    private final CompilationRepository compilationRepository;
    private final CategoryRepository categoryRepository;
    private final StatClient statClient;

    @Override
    public List<EventShortDto> getEvents(SelectionConditionForPublic condition) {
        return null;
    }

    @Override
    public EventFullDto getEventById(long eventId, HttpServletRequest request) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

        if (event.getState() != State.PUBLISHED) {
            throw new RequestConditionException("Event не опубликован");
        }

        saveStats(request.getRequestURI(), request.getRemoteAddr());
        event = getStats(event, new String[]{request.getRequestURI()});

        return toEventFull(event);
    }

    private Event getStats(Event event, String[] uris) {
        ResponseEntity<Object> response = statClient.getStats(event.getCreated(), LocalDateTime.now(),
                uris, false);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Сервер сейчас не доступен");
        }
        List<Object> stats = objectMapper.convertValue(response.getBody(), List.class);
                List<ViewStats> ss = stats
                        .stream()
                .map(o -> objectMapper.convertValue(o, ViewStats.class)).collect(Collectors.toList());
      //  stats.stream(obj -> objectMapper.convertValue(obj, ViewStats.class))
        //event.setViews(stats.getHits());
        ViewStats stats1 = (ViewStats) stats.get(0);
        log.info(stats1.toString());
        return event;
    }

    private void saveStats(String requestURI, String remoteAddr) {
        EndpointHit endpointHit = new EndpointHit("server", requestURI, remoteAddr, LocalDateTime.now());

        ResponseEntity<Object> response = statClient.addHit(endpointHit);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Сервер сейчас не доступен");
        }
    }

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, int from, int size) {
        List<Compilation> compilations = pinned == null ? compilationRepository.findAll(makePageable(from, size)).getContent() :
                compilationRepository.findAllByPinned(pinned, makePageable(from, size));

        return compilations.stream().map(CompilationsMapper::toCompilationDto).collect(Collectors.toList());
    }

    @Override
    public CompilationDto getCompilationById(Long compilationId) {
        return compilationRepository.findById(compilationId).map(CompilationsMapper::toCompilationDto)
                .orElseThrow(() -> new CompilationNotFoundException(compilationId));
    }

    @Override
    public List<CategoryDto> getCategories(int from, int size) {
        return categoryRepository.findAll(makePageable(from, size)).map(CategoryMapper::toDto).toList();
    }

    @Override
    public CategoryDto getCategoryById(long categoryId) {
        return categoryRepository.findById(categoryId).map(CategoryMapper::toDto)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
    }
}
