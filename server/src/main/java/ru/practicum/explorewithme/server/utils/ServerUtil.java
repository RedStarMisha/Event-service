package ru.practicum.explorewithme.server.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ServerUtil {

    public static Pageable makePageable(int from, int size) {
        int page = from / size;
        return PageRequest.of(page, size);
    }
}
