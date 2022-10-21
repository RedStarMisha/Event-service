package ru.practicum.explorewithme.models;

import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.List;

@Value
public class ApiError {
    List<FieldError> errors;
    String message;
    String reason;
    HttpStatus status;
    LocalDateTime timestamp;
}
