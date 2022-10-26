package ru.practicum.explorewithme.server.services;

import ru.practicum.explorewithme.models.category.CategoryDto;
import ru.practicum.explorewithme.models.compilation.CompilationDto;
import ru.practicum.explorewithme.models.event.EventFullDto;
import ru.practicum.explorewithme.models.event.EventShortDto;
import ru.practicum.explorewithme.server.utils.SelectionConditionForPublic;

import java.util.List;

public interface PublicService {

    List<EventShortDto> getEvents(SelectionConditionForPublic condition);

    EventFullDto getEventById(int eventId);

    List<CompilationDto> getCompilations(boolean pinned, int from, int size);

    CompilationDto getCompilationById(Long compilationId);

    List<CategoryDto> getCategories(int from, int size);

    CategoryDto getCategoryById(long categoryId);
}
