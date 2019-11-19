package com.mrathena.spring.boot.starter.toolkit.verify.annotation;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.Arrays;

/**
 * @author mrathena on 2019/11/14 14:04
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Constraint(validatedBy = InStringArray.Validator.class)
public @interface InStringArray {

	String message() default "值必须为'members'之一";

	/**
	 * 可选值
	 */
	String[] members();

	/**
	 * 当前值与可选值是否忽略大小写差异
	 */
	boolean ignoreCase() default true;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	// ----------

	class Validator implements ConstraintValidator<InStringArray, String> {

		private String[] members;
		private Boolean ignoreCase;

		@Override
		public void initialize(InStringArray constraintAnnotation) {
			this.members = constraintAnnotation.members();
			this.ignoreCase = constraintAnnotation.ignoreCase();
		}

		@Override
		public boolean isValid(String s, ConstraintValidatorContext context) {
			if (members == null || members.length == 0) {
				return false;
			}
			if (ignoreCase) {
				return Arrays.stream(members).anyMatch(item -> item.equalsIgnoreCase(s));
			} else {
				return Arrays.asList(members).contains(s);
			}
		}

	}

}