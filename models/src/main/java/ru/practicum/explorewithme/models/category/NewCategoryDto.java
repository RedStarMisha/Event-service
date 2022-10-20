package ru.practicum.explorewithme.models.category;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
public class NewCategoryDto {
    @NotBlank
    String name;
}
