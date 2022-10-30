package ru.practicum.explorewithme.statistics;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.LocalDateTimeConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stats")
@Data
@NoArgsConstructor
public class Statistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "app")
    private String app;

    @Column(name = "uri")
    private String uri;
    @Column(name = "ip")
    private String ip;

    @Column(name = "date_request")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime timestamp;

    @Column(name = "event")
    private long event;

    public Statistic(String app, String uri, String ip, LocalDateTime timestamp, long event) {
        this.app = app;
        this.uri = uri;
        this.ip = ip;
        this.timestamp = timestamp;
        this.event = event;
    }
}