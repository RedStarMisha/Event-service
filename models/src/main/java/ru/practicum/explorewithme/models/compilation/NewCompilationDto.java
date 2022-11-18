package ru.practicum.explorewithme.models.compilation;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class NewCompilationDto {

    long[] events;

    Boolean pinned = false;

    @NotBlank
    String title;
}
