package ru.practicum.explorewithme.server.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.models.user.UserDto;
import ru.practicum.explorewithme.server.models.User;

import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<UserDto> findAllByIdIsIn(long[] ids, Pageable pageable);
}
