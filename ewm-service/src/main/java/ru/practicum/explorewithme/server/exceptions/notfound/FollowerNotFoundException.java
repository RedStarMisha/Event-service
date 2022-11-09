package ru.practicum.explorewithme.server.exceptions.notfound;

public class FollowerNotFoundException extends EntityNotFoundException {

    public FollowerNotFoundException(long id) {
        super("Follower", id);
    }

    @Override
    public String getEntityType() {
        return "Follower";
    }
}
