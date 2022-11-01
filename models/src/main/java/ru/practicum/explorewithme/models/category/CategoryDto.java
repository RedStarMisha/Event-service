package ru.practicum.explorewithme.models.category;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
public class CategoryDto {
    @NotNull
    @Positive
    private Long id;
    @NotBlank
    private String name;
}
