package ru.practicum.explorewithme.server.services;

import ru.practicum.explorewithme.models.category.CategoryDto;
import ru.practicum.explorewithme.models.compilation.CompilationDto;
import ru.practicum.explorewithme.models.event.EventFullDto;
import ru.practicum.explorewithme.models.event.EventShortDto;
import ru.practicum.explorewithme.server.utils.selectioncondition.SelectionConditionForPublic;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PublicService {

    List<EventShortDto> getEvents(SelectionConditionForPublic condition, HttpServletRequest request);

    EventFullDto getEventById(long eventId, HttpServletRequest request);

    List<CompilationDto> getCompilations(Boolean pinned, int from, int size);

    CompilationDto getCompilationById(Long compilationId);

    List<CategoryDto> getCategories(int from, int size);

    CategoryDto getCategoryById(long categoryId);
}
