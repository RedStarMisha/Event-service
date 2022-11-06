package ru.practicum.explorewithme.models.event;

import java.util.Optional;

public enum EventState {
    FUTURE,
    PAST,
    AVAILABLE,
    ALL;

    public static Optional<EventState> from(String stateString) {
        for (EventState state : values()) {
            if (state.name().equalsIgnoreCase(stateString)) {
                return Optional.of(state);
            }
        }
        return Optional.empty();
    }
}
