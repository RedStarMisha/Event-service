package ru.practicum.explorewithme.server.utils;

import lombok.Getter;
import ru.practicum.explorewithme.models.event.EventSort;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class SelectionConditionForPublic {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private String text;
    private int[] categories;
    private Boolean paid;
    private LocalDateTime rangeStart;
    private LocalDateTime rangeEnd;
    private boolean available;
    private EventSort sort;
    private int from;
    private int size;

    private SelectionConditionForPublic(String text, int[] categories, Boolean paid, LocalDateTime rangeStart,
                                       LocalDateTime rangeEnd, boolean available, EventSort sort, int from, int size) {
        this.text = text;
        this.categories = categories;
        this.paid = paid;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
        this.available = available;
        this.sort = sort;
        this.from = from;
        this.size = size;
    }

    public static SelectionConditionForPublic of (String text, int[] categories, Boolean paid, String rangeStart,
                                              String rangeEnd, boolean available, EventSort sort, int from, int size) {

        LocalDateTime start = rangeStart != null ? LocalDateTime.parse(rangeStart, formatter) : null;
        LocalDateTime end = rangeEnd != null ? LocalDateTime.parse(rangeEnd, formatter) : null;

        return new SelectionConditionForPublic(text, categories, paid, start, end, available, sort, from, size);
    }
}
