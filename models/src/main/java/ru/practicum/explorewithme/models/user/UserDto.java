package ru.practicum.explorewithme.models.user;

import lombok.Value;

@Value
public class UserDto {

    long id;

    String name;

    String email;
}
