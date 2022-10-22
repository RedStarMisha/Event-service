package ru.practicum.explorewithme;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.explorewithme.models.ApiError;

@RestControllerAdvice
public class GatewayExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError validationException(MethodArgumentNotValidException e) {
        return new ApiError(e.getFieldErrors(), e.getMessage(), "The request was formed incorrectly",
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Throwable.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError serverException(Throwable e) {
        return new ApiError(e.getMessage(), "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
