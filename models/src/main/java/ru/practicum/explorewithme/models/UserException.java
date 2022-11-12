package ru.practicum.explorewithme.models;

public class UserException extends RuntimeException {

    public UserException() {
        super("А вот и ошибка");
    }
}
