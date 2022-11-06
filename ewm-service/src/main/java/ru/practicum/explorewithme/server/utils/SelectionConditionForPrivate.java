package ru.practicum.explorewithme.server.utils;

import ru.practicum.explorewithme.models.event.EventState;

import java.time.LocalDateTime;

import static ru.practicum.explorewithme.server.utils.ServerUtil.convertToDate;

public class SelectionConditionForPrivate {
    private final EventState state;
    private final LocalDateTime rangeStart;
    private final LocalDateTime rangeEnd;
    private final Boolean available;
    private final int from;
    private final int size;

    private SelectionConditionForPrivate(EventState state, LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                        Boolean available, int from, int size) {
        this.state = state;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
        this.available = available;
        this.from = from;
        this.size = size;
    }

    public static SelectionConditionForPrivate of(EventState state, String start, String end, Boolean available,
                                           int from, int size) {
        LocalDateTime startDate = convertToDate(start);
        LocalDateTime endDate = convertToDate(end);
        return new SelectionConditionForPrivate(state, startDate, endDate, available, from, size);
    }


}
