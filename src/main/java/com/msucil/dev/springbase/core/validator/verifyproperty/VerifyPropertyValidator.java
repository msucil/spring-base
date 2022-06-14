package com.msucil.dev.springbase.core.validator.verifyproperty;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class VerifyPropertyValidator implements ConstraintValidator<VerifyProperty, Object> {

    private String property;
    private String verifyWith;

    @Override
    public void initialize(VerifyProperty constraintAnnotation) {
        property = constraintAnnotation.property();
        verifyWith = constraintAnnotation.verifyWith();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        final BeanWrapper wrapper = new BeanWrapperImpl(o);

        final String baseValue = (String) wrapper.getPropertyValue(this.property);
        final String compareValue = (String) wrapper.getPropertyValue(this.verifyWith);

        final boolean isValid = StringUtils.isNotBlank(baseValue)
            && StringUtils.isNotBlank(compareValue)
            && baseValue.equals(compareValue);

        if (!isValid) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    constraintValidatorContext.getDefaultConstraintMessageTemplate())
                .addPropertyNode(property).addConstraintViolation();
        }

        return isValid;
    }
}
