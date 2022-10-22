package ru.practicum.explorewithme.models.category;

import lombok.Data;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class NewCategoryDto {
    @NotBlank
    String name;
}
