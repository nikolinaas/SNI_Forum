package org.unibl.etf.ip.sni_projekat.validation.sql;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SQLValidator.class)
@Documented
public @interface SQLValidation {

     String message() default "System recognized SQLInjection!";
     Class<?>[] groups() default { };

     Class<? extends Payload>[] payload() default { };
}
