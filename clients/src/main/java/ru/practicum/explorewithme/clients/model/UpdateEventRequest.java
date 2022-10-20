package ru.practicum.explorewithme.clients.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class UpdateEventRequest {
    @NotNull
    @Positive
    private Long id;
}
