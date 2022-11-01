package ru.practicum.explorewithme.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.server.models.Loc;

import java.util.Optional;

@Repository
public interface LocRepository extends JpaRepository<Loc, Long> {
    Optional<Loc> findByLatitudeAndLongitude(float latitude, float longitude);
}
