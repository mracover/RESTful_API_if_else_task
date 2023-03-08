package com.mracover.if_else_task.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StringValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@ReportAsSingleViolation
public @interface ValidateString {
    Class<? extends Enum<?>> enumClazz();

    String message() default "Value is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
