package ru.practicum.explorewithme.server.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "compilations")
@NoArgsConstructor
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "pinned")
    private boolean pinned;

    @ManyToMany
    @JoinTable(
            name = "events_compilations",
            joinColumns = @JoinColumn(name = "compilation_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<Event> events;

    public Compilation(String title, boolean pinned, List<Event> events) {
        this.title = title;
        this.pinned = pinned;
        this.events = events;
    }

    public void deleteEvent(Event event) {
        events.remove(event);
    }

    public void addEvent(Event event) {
        events.add(event);
    }
}
