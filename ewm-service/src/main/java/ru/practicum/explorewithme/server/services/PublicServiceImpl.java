package ru.practicum.explorewithme.server.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.models.category.CategoryDto;
import ru.practicum.explorewithme.models.compilation.CompilationDto;
import ru.practicum.explorewithme.models.event.EventFullDto;
import ru.practicum.explorewithme.models.event.EventShortDto;
import ru.practicum.explorewithme.models.event.EventSort;
import ru.practicum.explorewithme.models.event.State;
import ru.practicum.explorewithme.server.exceptions.notfound.CategoryNotFoundException;
import ru.practicum.explorewithme.server.exceptions.notfound.CompilationNotFoundException;
import ru.practicum.explorewithme.server.exceptions.notfound.EventNotFoundException;
import ru.practicum.explorewithme.server.exceptions.requestcondition.RequestConditionException;
import ru.practicum.explorewithme.server.models.Compilation;
import ru.practicum.explorewithme.server.models.Event;
import ru.practicum.explorewithme.server.models.QEvent;
import ru.practicum.explorewithme.server.repositories.CategoryRepository;
import ru.practicum.explorewithme.server.repositories.CompilationRepository;
import ru.practicum.explorewithme.server.repositories.EventRepository;
import ru.practicum.explorewithme.server.utils.selectioncondition.SearchParam;
import ru.practicum.explorewithme.server.utils.selectioncondition.SelectionConditionForPublic;
import ru.practicum.explorewithme.server.utils.mappers.CategoryMapper;
import ru.practicum.explorewithme.server.utils.mappers.CompilationsMapper;
import ru.practicum.explorewithme.server.utils.mappers.EventMapper;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.explorewithme.server.utils.ServerUtil.makePageable;
import static ru.practicum.explorewithme.server.utils.mappers.CompilationsMapper.toCompilationDto;
import static ru.practicum.explorewithme.server.utils.mappers.EventMapper.toEventFull;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class PublicServiceImpl implements PublicService {
    private final EventRepository eventRepository;
    private final CompilationRepository compilationRepository;
    private final CategoryRepository categoryRepository;
    private final StatsHandler statsHandler;

    @Override
    public List<EventShortDto> getEvents(SelectionConditionForPublic condition, HttpServletRequest request) {
        log.info("Запрошены Events с параметрами поиска {}", condition);
        QEvent qEvent = QEvent.event;
        SearchParam param = condition.getSearchParameters(qEvent);

        List<Event> list = eventRepository.findAll(param.getBooleanExpression(), param.getPageable()).toList();

        if (condition.getSort() != null && condition.getSort() == EventSort.VIEWS) {
            list = list.stream().sorted().collect(Collectors.toList());
        }

        return list.stream().peek(event -> statsHandler.statsHandle(event, request.getRemoteAddr()))
                .map(EventMapper::toEventShort).collect(Collectors.toList());
    }

    @Override
    public EventFullDto getEventById(long eventId, HttpServletRequest request) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

        if (event.getState() != State.PUBLISHED) {
            throw new RequestConditionException("Event не опубликован");
        }

        event = statsHandler.statsHandle(event, request.getRequestURI(), request.getRemoteAddr());

        log.info("Запрошен Event c id = {}", eventId);

        return toEventFull(event);
    }

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, int from, int size) {
        List<Compilation> compilations;
        if (pinned == null) {
            compilations = compilationRepository.findAll(makePageable(from, size)).getContent();
            log.info("Запрошены Compilations с оплатой - null");

        } else {
            compilations = compilationRepository.findAllByPinned(pinned, makePageable(from, size));
            log.info("Запрошены Compilations с оплатой - {}", pinned);
        }

        return compilations.stream().map(CompilationsMapper::toCompilationDto).collect(Collectors.toList());
    }

    @Override
    public CompilationDto getCompilationById(Long compilationId) {
        log.info("Запрошена Compilation с id = {}", compilationId);
        Compilation compilation = compilationRepository.findById(compilationId)
                .orElseThrow(() -> new CompilationNotFoundException(compilationId));

        log.info("Запрошенный {}", compilation);
        return toCompilationDto(compilation);
    }

    @Override
    public List<CategoryDto> getCategories(int from, int size) {
        log.info("Запрошен список Categories");
        return categoryRepository.findAll(makePageable(from, size)).map(CategoryMapper::toDto).toList();
    }

    @Override
    public CategoryDto getCategoryById(long categoryId) {
        log.info("Запрошена Category с id = {}", categoryId);

        return categoryRepository.findById(categoryId).map(CategoryMapper::toDto)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
    }
}
