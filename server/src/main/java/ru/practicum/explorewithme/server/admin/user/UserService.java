package ru.practicum.explorewithme.server.admin.user;

import ru.practicum.explorewithme.models.user.NewUserRequest;
import ru.practicum.explorewithme.models.user.UserDto;
import java.util.List;

public interface UserService {
    List<UserDto> getUsers(long[] ids, int from, int size);

    UserDto addUser(NewUserRequest newUserRequest);

    void deleteUser(long userId);
}
