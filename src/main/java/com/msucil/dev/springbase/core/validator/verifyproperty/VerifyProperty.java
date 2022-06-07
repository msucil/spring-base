package com.msucil.dev.springbase.core.validator.verifyproperty;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = VerifyPropertyValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface VerifyProperty {

    String message() default "Properties does not match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String property();

    String verifyWith();
}
