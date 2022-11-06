package ru.practicum.explorewithme.server.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ServerUtil {

    public static Pageable makePageable(int from, int size) {
        int page = from / size;
        return PageRequest.of(page, size);
    }

    public static Pageable makePageable(int from, int size, Sort sort) {
        int page = from / size;
        return PageRequest.of(page, size, sort);
    }

    public static LocalDateTime convertToDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return date != null && !date.equals("") ? LocalDateTime.parse(date, formatter) : null;
    }
}
