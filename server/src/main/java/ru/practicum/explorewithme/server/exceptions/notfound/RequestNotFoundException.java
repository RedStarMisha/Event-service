package ru.practicum.explorewithme.server.exceptions.notfound;

public class RequestNotFoundException extends EntityNotFoundException {

    public RequestNotFoundException(long id) {
        super(String.format("Запрос на участие с id = %d не найден", id));
    }

    @Override
    public String getEntityType() {
        return "ParticipationRequest";
    }
}
