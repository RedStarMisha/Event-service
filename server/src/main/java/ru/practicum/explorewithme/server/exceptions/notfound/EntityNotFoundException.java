package ru.practicum.explorewithme.server.exceptions.notfound;

public abstract class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }

    public abstract String getEntityType();
}
