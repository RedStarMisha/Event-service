package ru.practicum.explorewithme.server.user;

import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.models.user.NewUserRequest;
import ru.practicum.explorewithme.models.user.UserDto;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public List<UserDto> getUsers(long[] ids, int from, int size) {
        return null;
    }

    @Override
    public UserDto addUser(NewUserRequest newUserRequest) {
        return new UserDto(1L, "петя", "asd@ya.ru");
    }

    @Override
    public void deleteUser(long userId) {

    }
}
