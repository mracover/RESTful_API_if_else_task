package com.mracover.if_else_task.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class StringValidator implements ConstraintValidator<ValidateString, String> {
    List<String> valueList = null;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return valueList.contains(value);
    }

    @Override
    public void initialize(ValidateString constraintAnnotation) {
        valueList = new ArrayList<>();
        Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClazz();

        @SuppressWarnings("rawtypes")
        Enum[] enumValArr = enumClass.getEnumConstants();

        for (@SuppressWarnings("rawtypes") Enum enumVal : enumValArr) {
            valueList.add(enumVal.toString().toUpperCase());
        }
    }

}
