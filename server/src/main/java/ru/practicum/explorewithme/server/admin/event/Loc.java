package ru.practicum.explorewithme.server.admin.event;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.models.event.Location;

import javax.persistence.*;

@Entity
@Data
@Table(name = "locations")
@NoArgsConstructor
public class Loc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "lat")
    private float latitude;

    @Column(name = "lon")
    private float longitude;

    public Loc(Location location) {
        this.latitude = location.getLat();
        this.longitude = location.getLon();
    }
}
