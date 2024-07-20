package com.mf.minutefictionbackend.annotations;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = MaxWordCountValidator.class)
public @interface MaxWordCount {
    String message() default "Exceeds maximum word count.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default  { };
}
