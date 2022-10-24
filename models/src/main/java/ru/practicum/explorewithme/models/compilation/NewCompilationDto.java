package ru.practicum.explorewithme.models.compilation;

import lombok.Data;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class NewCompilationDto {
    @NotNull
    @NotEmpty
    long[] events;

    @NotNull
    Boolean pinned;

    @NotBlank
    String title;
}
