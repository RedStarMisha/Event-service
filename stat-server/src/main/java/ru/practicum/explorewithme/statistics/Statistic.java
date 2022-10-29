package ru.practicum.explorewithme.statistics;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class Statistic {
    private Long id;

    private String app;

    private String uri;

    private String ip;
    @Convert(converter = LocalDateTimeC)
    private LocalDateTime timestamp;
}