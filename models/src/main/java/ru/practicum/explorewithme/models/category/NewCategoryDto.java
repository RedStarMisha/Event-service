package ru.practicum.explorewithme.models.category;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class NewCategoryDto {
    @NotBlank
    String name;
}
