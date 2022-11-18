package ru.practicum.explorewithme.models.subscription;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateFollowerDto {
    @NotNull
    private long groupId;
}
