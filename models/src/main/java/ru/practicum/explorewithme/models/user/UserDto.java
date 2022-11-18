package ru.practicum.explorewithme.models.user;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class UserDto {

    long id;

    String name;

    String email;
}
