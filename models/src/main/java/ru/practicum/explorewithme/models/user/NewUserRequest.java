package ru.practicum.explorewithme.models.user;

import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Value
public class NewUserRequest {
    @NotBlank
    String name;
    @Email
    @NotBlank
    String email;
}
