package ru.practicum.explorewithme.models;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Value
@AllArgsConstructor
public class ApiError {
    List<FieldError> errors;
    String message;
    String reason;
    HttpStatus status;
    LocalDateTime timestamp = LocalDateTime.now();

    public ApiError(String message, String reason, HttpStatus status) {
        errors = new ArrayList<>();
        this.message = message;
        this.reason = reason;
        this.status = status;
    }
}
