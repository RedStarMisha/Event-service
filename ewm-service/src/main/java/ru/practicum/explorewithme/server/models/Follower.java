package ru.practicum.explorewithme.server.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
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

    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "friendship_group")
    private FriendshipGroup group;

    @Convert(converter = LocalDateTimeConverter.class)
    @Column(name = "added")
    private LocalDateTime added = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "publisher")
    private User publisher;

    @ManyToOne
    @JoinColumn(name = "follower")
    private User follower;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subscription", referencedColumnName = "id")
    private SubscriptionRequest request;

    public Follower(FriendshipGroup group, User publisher, User follower, SubscriptionRequest request) {
        this.group = group;
        this.publisher = publisher;
        this.follower = follower;
        this.request = request;
    }

    public Follower(User publisher, User follower, SubscriptionRequest request) {
        this.publisher = publisher;
        this.follower = follower;
        this.request = request;
    }
}
