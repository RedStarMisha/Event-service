package ru.practicum.explorewithme.server.exceptions;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(long userId) {
        super(String.format("Пользователь с id = %d не найден", userId));
    }
}
