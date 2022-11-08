package ru.practicum.explorewithme.server.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.models.subscription.FriendshipGroup;
import ru.practicum.explorewithme.server.utils.LocalDateTimeConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "followers")
public class Follower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "group_level")
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

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "subscription", referencedColumnName = "id")
    private SubscriptionRequest request;

    public Follower(Group group, User publisher, User follower, SubscriptionRequest request) {
        this.group = group;
        this.publisher = publisher;
        this.follower = follower;
        this.request = request;
    }

    public Follower(User publisher, User follower, SubscriptionRequest request) {
        this.publisher = publisher;
        this.follower = follower;
        this.request = request;
        group = new Group(publisher, FriendshipGroup.FOLLOWER.name());
    }
}
