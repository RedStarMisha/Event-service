package ru.practicum.explorewithme.server.admin.event;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.models.State;
import ru.practicum.explorewithme.server.LocalDateTimeConverter;
import ru.practicum.explorewithme.server.admin.category.Category;
import ru.practicum.explorewithme.server.admin.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Data
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "annotation")
    private String annotation;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "initiator")
    private User initiator;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    @Column(name = "created")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime created;

    @Convert(converter = LocalDateTimeConverter.class)
    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @Convert(converter = LocalDateTimeConverter.class)
    @Column(name = "published")
    private LocalDateTime published;

    @ManyToOne
    @JoinColumn(name = "location")
    private Loc location;

    @Column(name = "paid")
    private boolean paid;

    @Column(name = "partition_limit")
    private int participantLimit;

    @Column(name = "moderation")
    private boolean moderation;

    @Enumerated(EnumType.ORDINAL)
    private State state;


}
