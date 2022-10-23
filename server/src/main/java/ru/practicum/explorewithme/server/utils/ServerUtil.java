package ru.practicum.explorewithme.server.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class ServerUtil {

    public static Pageable makePageable(int from, int size) {
        int page = from / size;
        return PageRequest.of(page, size);
    }
}
