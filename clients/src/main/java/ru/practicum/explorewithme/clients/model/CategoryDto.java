package ru.practicum.explorewithme.clients.model;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Value
public class CategoryDto {
    @NotNull
    @Positive
    long id;
    @NotBlank
    String name;
}
