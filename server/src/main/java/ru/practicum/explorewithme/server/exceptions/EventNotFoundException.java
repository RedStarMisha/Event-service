package ru.practicum.explorewithme.server.exceptions;

public class EventNotFoundException extends EntityNotFoundException {

    public EventNotFoundException(long id) {
        super(String.format("Событие с id = %d не найдено", id));
    }

    @Override
    public String getEntityType() {
        return "Event";
    }

}
