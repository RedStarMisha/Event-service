package ru.practicum.explorewithme.server.exceptions.notfound;

public class FollowerNotFoundException extends EntityNotFoundException {

    public FollowerNotFoundException(long id) {
        super("Follower", id);
    }

    public FollowerNotFoundException(String title) {
        super("Follower", title);
    }

    @Override
    public String getEntityType() {
        return "Follower";
    }
}
