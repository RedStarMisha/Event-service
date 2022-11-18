package ru.practicum.explorewithme.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.server.models.Group;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByUser_IdAndTitleIgnoreCase(long userId, String title);

    Optional<Group> findByIdAndUser_Id(long groupId, long userId);

    List<Group> findAllByUser_Id(long userId);
}
