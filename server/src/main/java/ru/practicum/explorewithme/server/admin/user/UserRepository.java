package ru.practicum.explorewithme.server.admin.user;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithme.models.user.UserDto;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<UserDto> findAllByIdIsIn(long[] ids, Pageable pageable);
}
