package ru.practicum.explorewithme.models;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Value
@AllArgsConstructor
public class ApiError {
    StackTraceElement[] errors;
    String message;
    String reason;
    HttpStatus status;
    LocalDateTime timestamp = LocalDateTime.now();
}
