package com.mf.minutefictionbackend.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.StringTokenizer;

public class MaxWordCountValidator implements ConstraintValidator<MaxWordCount, String> {

    @Override
    public void initialize(MaxWordCount constraintAnnotation) {
    }
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        StringTokenizer tokenizer = new StringTokenizer(value);
        int wordCount = tokenizer.countTokens();

        return wordCount <= 100;
    }
}
