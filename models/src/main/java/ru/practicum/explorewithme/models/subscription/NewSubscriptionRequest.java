package ru.practicum.explorewithme.models.subscription;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class NewSubscriptionRequest {
    @NotNull
    private boolean friendship;
}
