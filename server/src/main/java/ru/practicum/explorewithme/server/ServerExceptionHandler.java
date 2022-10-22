package ru.practicum.explorewithme.server;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.explorewithme.models.ApiError;
import ru.practicum.explorewithme.server.exceptions.CategoryNotFoundException;
import ru.practicum.explorewithme.server.exceptions.EntityNotFoundException;
import ru.practicum.explorewithme.server.exceptions.UserNotFoundException;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ServerExceptionHandler {

    @ExceptionHandler({DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError databaseException(DataIntegrityViolationException e) {
        return new ApiError(e.getMessage(), "Integrity constraint has been violated data", HttpStatus.CONFLICT);
    }
    @ExceptionHandler({UserNotFoundException.class, CategoryNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError entityNotFoundException(EntityNotFoundException e) {
        return new ApiError(e.getMessage(), String.format("Requested %s not found", e.getEntityType()),
                HttpStatus.NOT_FOUND);
    }
}
