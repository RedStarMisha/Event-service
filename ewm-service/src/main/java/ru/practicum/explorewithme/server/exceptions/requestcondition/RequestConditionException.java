package ru.practicum.explorewithme.server.exceptions.requestcondition;

public class RequestConditionException extends RuntimeException {

    public RequestConditionException(String message) {
        super(message);
    }

    public String getReason() {
        return "For the requested operation the conditions are not met";
    }
}
