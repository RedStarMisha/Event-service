package ru.practicum.explorewithme.server.admin.user;

import ru.practicum.explorewithme.models.user.NewUserRequest;
import ru.practicum.explorewithme.models.user.UserDto;
import ru.practicum.explorewithme.models.user.UserShortDto;

public class UserMapper {
    public static User toUser(NewUserRequest newUserRequest) {
        return new User(newUserRequest.getName(), newUserRequest.getEmail());
    }

    public static UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

    public static UserShortDto toUserShort(User user) {
        return new UserShortDto(user.getId(), user.getName());
    }
}
