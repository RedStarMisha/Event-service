package ru.practicum.explorewithme;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.explorewithme.models.ApiError;

import java.time.LocalDateTime;

@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError validationException(MethodArgumentNotValidException e) {
        return new ApiError(e.getFieldErrors(), e.getMessage(), "Ошибка валидации", HttpStatus.BAD_REQUEST,
                LocalDateTime.now());
    }
}
