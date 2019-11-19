package com.mrathena.spring.boot.starter.toolkit.verify.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author mrathena on 2019/11/18 18:16
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@InStringArray(members = {"APP", "H5"}, message = "渠道类型不正确")
public @interface Channel {

	String message() default "";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
