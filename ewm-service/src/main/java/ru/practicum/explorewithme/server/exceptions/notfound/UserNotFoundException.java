package ru.practicum.explorewithme.server.exceptions.notfound;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException(long userId) {
        super("User", userId);
    }

    @Override
    public String getEntityType() {
        return "User";
    }
}
