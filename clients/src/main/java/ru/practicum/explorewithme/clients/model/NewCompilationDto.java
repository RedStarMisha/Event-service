package ru.practicum.explorewithme.clients.model;

import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class NewCompilationDto {
    long[] events;
    boolean pinned;
    @NotBlank
    String title;
}
