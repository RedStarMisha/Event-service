package ru.practicum.explorewithme.server.services.admin.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.models.subscription.group.FriendshipGroup;
import ru.practicum.explorewithme.models.user.NewUserRequest;
import ru.practicum.explorewithme.models.user.UserDto;
import ru.practicum.explorewithme.server.exceptions.notfound.UserNotFoundException;
import ru.practicum.explorewithme.server.models.Group;
import ru.practicum.explorewithme.server.models.User;
import ru.practicum.explorewithme.server.repositories.GroupRepository;
import ru.practicum.explorewithme.server.repositories.UserRepository;
import ru.practicum.explorewithme.server.services.admin.UserService;
import ru.practicum.explorewithme.server.utils.mappers.MyMapper;

import javax.transaction.Transactional;
import java.util.List;

import static ru.practicum.explorewithme.server.utils.ServerUtil.makePageable;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    private final GroupRepository groupRepository;

    private final MyMapper mapper;

    @Override
    public List<UserDto> getUsers(long[] ids, int from, int size) {
        if (ids.length == 0) {
            log.info("Get all users");
            return repository.findAll(makePageable(from, size)).map(mapper::toUserDto).toList();
        }

        log.info("Get users with id {}", ids);
        return repository.findAllByIdIsIn(ids, makePageable(from, size));
    }

    @Override
    public UserDto addUser(NewUserRequest newUserRequest) {
        log.info("Add new user {}", newUserRequest);
        User user = repository.save(mapper.toUser(newUserRequest));

        groupRepository.save(new Group(user, FriendshipGroup.FRIENDS_ALL.name()));
        groupRepository.save(new Group(user, FriendshipGroup.FOLLOWER.name()));

        return mapper.toUserDto(user);
    }

    @Override
    public void deleteUser(long userId) {
        repository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        log.info("User with id = {} is deleted", userId);
        repository.deleteById(userId);
    }
}
