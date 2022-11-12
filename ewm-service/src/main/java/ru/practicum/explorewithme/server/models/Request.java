package ru.practicum.explorewithme.server.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.models.request.RequestStatus;
import ru.practicum.explorewithme.server.utils.LocalDateTimeConverter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "participation_requests")
@Data
@NoArgsConstructor
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "created")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime created = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "requestor")
    private User requestor;

    @ManyToOne
    @JoinColumn(name = "event")
    private Event event;

    @Enumerated(value = EnumType.ORDINAL)
    private RequestStatus status = RequestStatus.PENDING;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "request_group",
            joinColumns = @JoinColumn(name = "request"),
            inverseJoinColumns = @JoinColumn(name = "group_level"))
    private Set<Group> groups;


    public void addGroup(Group group) {
        groups.add(group);
    }

    public void deleteGroup(Group group){
        groups.remove(group);
    }

    private Request(User requestor, Event event, RequestStatus status) {
        this.requestor = requestor;
        this.event = event;
        this.status = status;
    }

    public static Request makePending(User requestor, Event event) {
        return new Request(requestor, event, RequestStatus.PENDING);
    }

    public static Request makeConfirmed(User requestor, Event event) {
        return new Request(requestor, event, RequestStatus.CONFIRMED);
    }
}
