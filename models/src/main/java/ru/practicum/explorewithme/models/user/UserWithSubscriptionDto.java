package ru.practicum.explorewithme.models.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserWithSubscriptionDto {
    private long id;

    private String name;

    private long followers;

    private long friends;
}
