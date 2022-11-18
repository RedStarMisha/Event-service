package ru.practicum.explorewithme.server.exceptions.notfound;

public class EventNotFoundException extends EntityNotFoundException {

    public EventNotFoundException(long id) {
        super("Event", id);
    }

    @Override
    public String getEntityType() {
        return "Event";
    }

}
