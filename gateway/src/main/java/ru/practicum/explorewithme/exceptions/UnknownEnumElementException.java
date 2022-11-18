package ru.practicum.explorewithme.exceptions;

public class UnknownEnumElementException extends RuntimeException {

    public UnknownEnumElementException(String message) {
        super("Неизвестный параметр - " + message);
    }
}
