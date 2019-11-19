package com.mrathena.spring.boot.starter.toolkit.verify.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import java.lang.annotation.*;

/**
 * @author mrathena on 2019/11/18 18:16
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@NotBlank
public @interface Token {

	String message() default "";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
