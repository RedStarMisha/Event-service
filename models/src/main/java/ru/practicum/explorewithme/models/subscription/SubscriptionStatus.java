package ru.practicum.explorewithme.models.subscription;

import java.util.Optional;

public enum SubscriptionStatus {
    WAITING,
    CONSIDER,
    REVOKE,
    CANCELED_BY_PUBLISHER,
    FOLLOWER,
    CANCELED_BY_FOLLOWER;

    public static Optional<SubscriptionStatus> from(String stringState) {
        for (SubscriptionStatus state : values()) {
            if (state.name().equalsIgnoreCase(stringState)) {
                return Optional.of(state);
            }
        }
        return Optional.empty();
    }
}
