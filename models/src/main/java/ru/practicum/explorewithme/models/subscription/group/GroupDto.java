package ru.practicum.explorewithme.models.subscription.group;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupDto {
    private long id;

    private long user;

    private String title;
}
