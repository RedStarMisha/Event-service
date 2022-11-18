package ru.practicum.explorewithme.models.validation;


import ru.practicum.explorewithme.models.event.EventDateCheckable;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class DateChecker implements ConstraintValidator<CheckEventDate, EventDateCheckable> {

    @Override
    public void initialize(CheckEventDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(EventDateCheckable dto, ConstraintValidatorContext constraintValidatorContext) {
        return dto.getEventDate() == null || dto.getEventDate().minusHours(2).isAfter(LocalDateTime.now());
    }
}