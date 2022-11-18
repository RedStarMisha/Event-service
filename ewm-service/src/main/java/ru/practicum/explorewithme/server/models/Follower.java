package ru.practicum.explorewithme.server.models;

import lombok.*;
import ru.practicum.explorewithme.server.utils.LocalDateTimeConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "followers")
@Entity
public class Follower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_level")
    @ToString.Exclude
    private Group group;

    @Convert(converter = LocalDateTimeConverter.class)
    @Column(name = "added")
    private LocalDateTime added = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "publisher")
    private User publisher;

    @ManyToOne
    @JoinColumn(name = "follower")
    private User follower;

    @OneToOne//(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "subscription", referencedColumnName = "id")
    private SubscriptionRequest request;

    public Follower(Group group, User publisher, User follower, SubscriptionRequest request) {
        this.group = group;
        this.publisher = publisher;
        this.follower = follower;
        this.request = request;
    }
}
