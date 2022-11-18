package ru.practicum.explorewithme.models.event;

import java.util.Arrays;
import java.util.Optional;

public enum EventSort {
    EVENT_DATE,
    VIEWS;

    public static Optional<EventSort> from(String param) {
        return Arrays.stream(EventSort.values()).filter(sort -> sort.toString().equals(param.toUpperCase())).findAny();
    }
}

