package ru.practicum.explorewithme.server.models;

import lombok.*;
import org.hibernate.annotations.Where;
import ru.practicum.explorewithme.server.utils.LocalDateTimeConverter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString()
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "created")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime created;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        created = LocalDateTime.now();
    }

//    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "followers",
//            joinColumns = @JoinColumn(name = "publisher"),
//            inverseJoinColumns = @JoinColumn(name = "follower")
//    )
    @OneToMany(mappedBy = "publisher", fetch = FetchType.LAZY)
    @ToString.Exclude
    @Where(clause = "friendship_group = NULL")
    private List<Follower> followers;

    @OneToMany(mappedBy = "publisher", fetch = FetchType.LAZY)
    @ToString.Exclude
    @Where(clause = "friendship_group != NULL")
    private List<Follower> friends;

//    create table if not exists followers (
//            id bigint generated always as identity primary key ,
//            friendship_group int,
//            added timestamp,
//            publisher bigint references users,
//            follower bigint references users,
//            subscription bigint references subscription
//    );

//    @ManyToMany
//    @JoinTable(
//            name = "subscription",
//            joinColumns = @JoinColumn(name = "compilation_id"),
//            inverseJoinColumns = @JoinColumn(name = "event_id")
//    )
//    private Set<User> followers;
//
//    private Set<User> friends;

//    @ManyToMany
//    @JoinTable(
//            name = "subscription",
//            joinColumns = @JoinColumn(name = "follower"),
//            inverseJoinColumns = @JoinColumn(name = "publisher")
//    )
//    private Set<User> subscribeRequests;

//    create table if not exists subscription (
//            id bigint generated always as identity primary key ,
//            publisher bigint references users,
//            follower bigint references users,
//            created timestamp,
//            updated timestamp,
//            state int
}
