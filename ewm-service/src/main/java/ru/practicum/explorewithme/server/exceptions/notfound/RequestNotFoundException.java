package ru.practicum.explorewithme.server.exceptions.notfound;

public class RequestNotFoundException extends EntityNotFoundException {

    public RequestNotFoundException(long id) {
        super("ParticipationRequest", id);
    }

    @Override
    public String getEntityType() {
        return "ParticipationRequest";
    }
}
