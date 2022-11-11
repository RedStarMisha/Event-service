package ru.practicum.explorewithme.validation;

import ru.practicum.explorewithme.exceptions.IncorrectDateException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidUtil {

    public static void dateValidation(String start, String end) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime rangeStart = start != null ? LocalDateTime.parse(start, formatter) : null;
            LocalDateTime rangeEnd = end != null ? LocalDateTime.parse(end, formatter) : null;
            if (start != null && end != null && rangeStart.isAfter(rangeEnd)) {
                throw new IncorrectDateException("Введенные даты некорректны");
            }
        } catch (DateTimeParseException e) {
            throw new IncorrectDateException("Введенные даты некорректны");
        }
    }
}
