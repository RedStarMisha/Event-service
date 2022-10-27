package ru.practicum.explorewithme.server.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.models.category.CategoryDto;
import ru.practicum.explorewithme.models.compilation.CompilationDto;
import ru.practicum.explorewithme.models.event.EventFullDto;
import ru.practicum.explorewithme.models.event.EventShortDto;
import ru.practicum.explorewithme.models.event.State;
import ru.practicum.explorewithme.server.exceptions.notfound.EventNotFoundException;
import ru.practicum.explorewithme.server.exceptions.requestcondition.RequestConditionException;
import ru.practicum.explorewithme.server.models.Event;
import ru.practicum.explorewithme.server.repositories.EventRepository;
import ru.practicum.explorewithme.server.utils.SelectionConditionForPublic;
import ru.practicum.explorewithme.server.utils.mappers.EventMapper;

import javax.transaction.Transactional;
import java.util.List;

import static ru.practicum.explorewithme.server.utils.mappers.EventMapper.toEventFull;

@Service
@Slf4j
@Transactional
@AllArgsConstructor(onConstructor_ = @Autowired)
public class PublicServiceImpl implements PublicService {
    private final EventRepository eventRepository;
    @Override
    public List<EventShortDto> getEvents(SelectionConditionForPublic condition) {
        return null;
    }

    @Override
    public EventFullDto getEventById(long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

        if (event.getState() != State.PUBLISHED) {
            throw new RequestConditionException("Event не опубликован");
        }

        return toEventFull(event);
    }

    @Override
    public List<CompilationDto> getCompilations(boolean pinned, int from, int size) {
        return null;
    }

    @Override
    public CompilationDto getCompilationById(Long compilationId) {
        return null;
    }

    @Override
    public List<CategoryDto> getCategories(int from, int size) {
        return null;
    }

    @Override
    public CategoryDto getCategoryById(long categoryId) {
        return null;
    }
}
