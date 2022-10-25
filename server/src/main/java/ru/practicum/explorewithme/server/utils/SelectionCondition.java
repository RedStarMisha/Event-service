package ru.practicum.explorewithme.server.utils;

import lombok.Getter;

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
}
