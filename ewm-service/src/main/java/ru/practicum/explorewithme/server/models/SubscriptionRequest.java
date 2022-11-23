package ru.practicum.explorewithme.server.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.models.subscription.SubscriptionStatus;
import ru.practicum.explorewithme.server.utils.LocalDateTimeConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "subscription")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubscriptionRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "friendship_request")
    private boolean friendshipRequest;

    @ManyToOne
    @JoinColumn(name = "publisher")
    private User publisher;

    @ManyToOne
    @JoinColumn(name = "follower")
    private User follower;

    @Column(name = "created")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime created = LocalDateTime.now();

    @Column(name = "updated")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime updated;

    @Enumerated(EnumType.ORDINAL)
    private SubscriptionStatus status;

    public SubscriptionRequest(boolean friendshipRequest, User publisher, User follower, SubscriptionStatus status) {
        this.friendshipRequest = friendshipRequest;
        this.publisher = publisher;
        this.follower = follower;
        this.status = status;
    }

    public SubscriptionRequest(boolean friendshipRequest, User publisher, User follower, LocalDateTime updated,
                               SubscriptionStatus status) {
        this.friendshipRequest = friendshipRequest;
        this.publisher = publisher;
        this.follower = follower;
        this.updated = updated;
        this.status = status;
    }
}