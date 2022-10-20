package ru.practicum.explorewithme.clients.model;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
public class NewCategoryDto {
    @NotBlank
    String name;
}
