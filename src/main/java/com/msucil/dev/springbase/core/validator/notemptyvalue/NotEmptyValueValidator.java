package com.msucil.dev.springbase.core.validator.notemptyvalue;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

public class NotEmptyValueValidator implements ConstraintValidator<NotEmptyValue, String> {

    @Override
    public void initialize(NotEmptyValue constraintAnnotation) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return StringUtils.isNotEmpty(s);
    }
}
