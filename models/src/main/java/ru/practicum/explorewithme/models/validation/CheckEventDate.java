package ru.practicum.explorewithme.models.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE_USE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateChecker.class)
public @interface CheckEventDate {

    String message() default "До начала события менее 2х часов";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}