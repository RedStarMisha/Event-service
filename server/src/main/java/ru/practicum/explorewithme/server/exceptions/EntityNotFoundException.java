package ru.practicum.explorewithme.server.exceptions;

public abstract class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }

    public abstract String getEntityType();
}
