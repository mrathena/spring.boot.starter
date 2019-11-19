package com.mrathena.spring.boot.starter.toolkit.verify.annotation;

import com.mrathena.common.exception.ServiceException;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author mrathena on 2019/11/19 10:26
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Constraint(validatedBy = InEnum.Validator.class)
public @interface InEnum {

	String message() default "值必须在指定枚举中";

	/**
	 * 枚举类
	 */
	Class<? extends Enum<?>> enumClass();

	/**
	 * 由枚举本身提供的验证参数是否合法的方法
	 */
	String enumMethod() default "isValid";

	/**
	 * 是否允许空参数通过校验
	 */
	boolean allowNull() default false;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	// ----------

	class Validator implements ConstraintValidator<InEnum, Object> {

		private Class<? extends Enum<?>> enumClass;
		private String enumMethod;
		private Boolean allowNull;

		@Override
		public void initialize(InEnum constraintAnnotation) {
			this.enumClass = constraintAnnotation.enumClass();
			this.enumMethod = constraintAnnotation.enumMethod();
			this.allowNull = constraintAnnotation.allowNull();
		}

		@Override
		public boolean isValid(Object value, ConstraintValidatorContext context) {
			if (null == value) {
				return allowNull;
			}
			if (null == enumClass || null == enumMethod) {
				return true;
			}
			Class<?> valueClass = value.getClass();
			try {
				Method method = enumClass.getMethod(enumMethod, valueClass);
				if (!Boolean.TYPE.equals(method.getReturnType()) && !Boolean.class.equals(method.getReturnType())) {
					String message = String.format("method %s return is not boolean type in the class %s", enumMethod, enumClass);
					throw new ServiceException(message, "@InEnum指定的校验方法的返回值不是Boolean");
				}
				if (!Modifier.isStatic(method.getModifiers())) {
					String message = String.format("method %s is not static method in the class %s", enumMethod, enumClass);
					throw new ServiceException(message, "@InEnum指定的校验方法不是static");
				}
				Boolean result = (Boolean) method.invoke(null, value);
				return result == null ? Boolean.FALSE : result;
			} catch (IllegalAccessException | InvocationTargetException e) {
				throw new ServiceException(e);
			} catch (NoSuchMethodException e) {
				String message = String.format("This method %s(%s) does not exist in the class %s", enumMethod, valueClass, enumClass);
				throw new ServiceException(message, "@InEnum指定的校验方法不存在");
			}
		}
	}

}
