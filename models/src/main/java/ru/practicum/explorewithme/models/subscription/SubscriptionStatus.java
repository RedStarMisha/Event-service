package ru.practicum.explorewithme.models.subscription;

import java.util.Optional;

public enum SubscriptionStatus {
    FRIENDSHIP,
    WAITING,
    SUBSCRIPTION,
    REVOKE,
    CANCELED;

    public static Optional<SubscriptionStatus> from(String stringState) {
        for (SubscriptionStatus state : values()) {
            if (state.name().equalsIgnoreCase(stringState)) {
                return Optional.of(state);
            }
        }
        return Optional.empty();
    }
}
