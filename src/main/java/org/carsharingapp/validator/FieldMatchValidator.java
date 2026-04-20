package org.carsharingapp.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;
import org.carsharingapp.annotation.FieldMatch;
import org.springframework.beans.BeanWrapperImpl;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    private String firstFieldName;

    private String secondFieldName;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            Object first = new BeanWrapperImpl(value).getPropertyValue(firstFieldName);
            Object second = new BeanWrapperImpl(value).getPropertyValue(secondFieldName);

            return Objects.equals(first, second);
        } catch (Exception e) {
            return false;
        }
    }
}
