package ru.practicum.explorewithme.models.compilation;

import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class NewCompilationDto {
    long[] events;

    boolean pinned;

    @NotBlank
    String title;
}
