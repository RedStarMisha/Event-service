package ru.practicum.explorewithme.server;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.explorewithme.models.ApiError;
import ru.practicum.explorewithme.server.exceptions.notfound.*;
import ru.practicum.explorewithme.server.exceptions.requestcondition.RequestConditionException;

@RestControllerAdvice
public class ServerExceptionHandler {

    @ExceptionHandler({DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError databaseException(DataIntegrityViolationException e) {
        return new ApiError(e.getStackTrace(), e.getMessage(), "Integrity constraint has been violated data",
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler({RequestConditionException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError requestConditionException(RequestConditionException e) {
        return new ApiError(e.getStackTrace(), e.getMessage(), e.getReason(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({UserNotFoundException.class, CategoryNotFoundException.class, EventNotFoundException.class,
            CompilationNotFoundException.class, RequestNotFoundException.class, GroupNotFoundException.class,
            FollowerNotFoundException.class, SubscriptionNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError entityNotFoundException(EntityNotFoundException e) {
        return new ApiError(e.getStackTrace(), e.getMessage(), "The required object was not found.",
                HttpStatus.NOT_FOUND);
    }
}
