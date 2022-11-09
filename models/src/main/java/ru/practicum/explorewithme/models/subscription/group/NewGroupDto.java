package ru.practicum.explorewithme.models.subscription.group;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class NewGroupDto {
    @NotBlank
    private String title;
}
