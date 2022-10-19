package ru.practicum.explorewithme.publicnode;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class DateRange {

    private LocalDateTime start;
    private LocalDateTime end;

    private DateRange(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    public static DateRange makeDateRange(String start, String end) {
        LocalDateTime rangeStart = parse(start);
        LocalDateTime rangeEnd = parse(end);
        if (rangeStart != null || rangeEnd != null) {
            return new DateRange(rangeStart, rangeEnd);
        } else {
            return null;
        }
    }

    private static LocalDateTime parse(String date) {
        return LocalDateTime.parse(date);
    }
}
