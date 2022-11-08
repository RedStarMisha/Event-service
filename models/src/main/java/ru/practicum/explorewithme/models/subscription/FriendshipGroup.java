package ru.practicum.explorewithme.models.subscription;

import java.util.Optional;

public enum FriendshipGroup {
    FOLLOWER,
    FRIENDS_ALL;

    public static Optional<FriendshipGroup> from(String stringGroup) {
        for (FriendshipGroup group : values()) {
            if (group.name().equalsIgnoreCase(stringGroup)) {
                return java.util.Optional.of(group);
            }
        }
        return java.util.Optional.empty();
    }
}
