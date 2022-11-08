package ru.practicum.explorewithme.server.exceptions.notfound;

public abstract class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message, long id) {
        super(message + " with id=" + id + " was not found");
    }

    public EntityNotFoundException(String message, String title) {
        super(message + " with name " + title + " was not found");
    }

    public abstract String getEntityType();
}
