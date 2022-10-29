package ru.practicum.explorewithme.models.statistics;

import lombok.Data;

@Data
public class ViewStats {
    private String app;

    private String uri;

    private long hits;
}
