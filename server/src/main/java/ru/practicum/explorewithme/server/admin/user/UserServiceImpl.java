package ru.practicum.explorewithme.server.admin.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.models.user.NewUserRequest;
import ru.practicum.explorewithme.models.user.UserDto;
import ru.practicum.explorewithme.server.exceptions.UserNotFoundException;

import javax.transaction.Transactional;
import java.util.List;

import static ru.practicum.explorewithme.server.admin.user.UserMapper.toUser;
import static ru.practicum.explorewithme.server.admin.user.UserMapper.toUserDto;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    @Override
    public List<UserDto> getUsers(@Nullable long[] ids, int from, int size) {

        if (ids == null) {
            log.info("Get all users");
            return repository.findAll(makePageable(from, size)).map(UserMapper::toUserDto).toList();
        }
        log.info("Get users with id {}", ids);
        return repository.findAllByIdIsIn(ids, makePageable(from, size));
    }

    @Override
    public UserDto addUser(NewUserRequest newUserRequest) {
        log.info("Add new user {}", newUserRequest);
        User user = toUser(newUserRequest);
        return toUserDto(repository.save(user));
    }

    @Override
    public void deleteUser(long userId) {
        repository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        log.info("User with id = {} is deleted", userId);
        repository.deleteById(userId);
    }

    private Pageable makePageable(int from, int size) {
        int page = from / size;
        return PageRequest.of(page, size);
    }
}
