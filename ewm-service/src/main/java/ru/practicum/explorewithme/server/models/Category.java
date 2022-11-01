package ru.practicum.explorewithme.server.models;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
@Table(name = "categories")
@NoArgsConstructor
public class Category {
    public Category(String name) {
        this.name = name;
    }

    public Category(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private Set<Event> events;


}
