package ru.practicum.explorewithme.server.utils;

import lombok.Getter;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.explorewithme.models.event.EventSort;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class SelectionCondition {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private int[] users;
    private int[] states;
    private int[] categories;
    private LocalDateTime rangeStart;
    private LocalDateTime rangeEnd;
    private int from;
    private int size;

    private SelectionCondition(int[] users, int[] states, int[] categories, LocalDateTime rangeStart,
                               LocalDateTime rangeEnd, int from, int size) {
        this.users = users;
        this.states = states;
        this.categories = categories;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
        this.from = from;
        this.size = size;
    }

    public static SelectionCondition of(int[] users, int[] states, int[] categories, String start, String end,
                                 int from, int size) {
        LocalDateTime startDate = start != null ? LocalDateTime.parse(start, formatter) : null;
        LocalDateTime endDate = start != null ? LocalDateTime.parse(end, formatter) : null;
        return new SelectionCondition(users, states, categories, startDate, endDate, from, size);
    }

    @RequestParam(name = "text", required = false) String text,
    @RequestParam(name = "categories", required = false) int[] categories,
    @RequestParam(name = "paid", required = false) Boolean paid,
    @RequestParam(name = "rangeStart", required = false) String rangeStart,
    @RequestParam(name = "rangeEnd", required = false) String rangeEnd,
    @RequestParam(name = "available", defaultValue = "false") boolean available,
    @RequestParam(name = "sort", defaultValue = "EVENT_DATE")
    EventSort sort,
    @RequestParam(name = "from", defaultValue = "0") int from,
    @RequestParam(name = "size", defaultValue = "10") int size
}
