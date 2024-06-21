package org.unibl.etf.ip.sni_projekat.validation.xss;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = XSSValidator.class)
@Documented
public @interface XSSValidation {

    String message() default "System recognized XSS!";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
