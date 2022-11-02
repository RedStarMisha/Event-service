package ru.practicum.explorewithme.server.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.models.category.CategoryDto;
import ru.practicum.explorewithme.models.compilation.CompilationDto;
import ru.practicum.explorewithme.models.event.EventFullDto;
import ru.practicum.explorewithme.models.event.EventShortDto;
import ru.practicum.explorewithme.models.event.EventSort;
import ru.practicum.explorewithme.server.services.PublicService;
import ru.practicum.explorewithme.server.utils.SelectionConditionForPublic;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PublicController {

    private final PublicService service;

    @GetMapping("/events")
    public List<EventShortDto> getEvents(@RequestParam(name = "text", required = false) String text,
                                         @RequestParam(name = "categories", required = false) int[] categories,
                                         @RequestParam(name = "paid", required = false) Boolean paid,
                                         @RequestParam(name = "rangeStart", required = false) String rangeStart,
                                         @RequestParam(name = "rangeEnd", required = false) String rangeEnd,
                                         @RequestParam(name = "available", required = false) Boolean available,
                                         @RequestParam(name = "sort", defaultValue = "EVENT_DATE") EventSort sort,
                                         @RequestParam(name = "from", defaultValue = "0") int from,
                                         @RequestParam(name = "size", defaultValue = "10") int size, HttpServletRequest request) {

        SelectionConditionForPublic condition = SelectionConditionForPublic.of(text, categories, paid, rangeStart,
                rangeEnd, available, sort, from, size);

        return service.getEvents(condition, request);
    }

    @GetMapping("/events/{eventId}")
    public EventFullDto getEventById(@PathVariable(name = "eventId") long eventId, HttpServletRequest request) {
        return service.getEventById(eventId, request);
    }

    @GetMapping("/compilations")
    public List<CompilationDto> getCompilations(@RequestParam(name = "pinned", required = false) Boolean pinned,
                                                @RequestParam(name = "from", defaultValue = "0") int from,
                                                @RequestParam(name = "size", defaultValue = "10") int size) {
        return service.getCompilations(pinned, from, size);
    }

    @GetMapping("/compilations/{compilationId}")
    public CompilationDto getCompilationById(@PathVariable(name = "compilationId") Long compilationId) {
        return service.getCompilationById(compilationId);
    }

    @GetMapping("/categories")
    public List<CategoryDto> getCategories(@RequestParam(name = "from", defaultValue = "0") int from,
                                           @RequestParam(name = "size", defaultValue = "10") int size) {
        return service.getCategories(from, size);
    }

    @GetMapping("/categories/{categoryId}")
    public CategoryDto getCategoryById(@PathVariable(name = "categoryId") long categoryId) {
        return service.getCategoryById(categoryId);
    }


}
