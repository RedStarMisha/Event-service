package ru.practicum.explorewithme.server.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "groups")
@NoArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "publisher")
    private User user;

    @Column(name = "title")
    private String title;

    public Group(User user, String title) {
        this.user = user;
        this.title = title;
    }
}
