package ru.practicum.explorewithme.server.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Where;
import ru.practicum.explorewithme.models.State;
import ru.practicum.explorewithme.server.utils.LocalDateTimeConverter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

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

    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
    @Where(clause = "status = 'CONFIRMED'")
    private Set<Request> confirmedRequests;

    @ManyToMany(mappedBy = "events")
    private Set<Compilation> compilations;


}
