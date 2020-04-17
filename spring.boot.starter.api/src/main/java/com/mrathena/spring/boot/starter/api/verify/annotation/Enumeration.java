package com.mrathena.spring.boot.starter.api.verify.annotation;

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
 * 枚举注解
 * 校验参数是否属于某个枚举的值
 *
 * @author com.mrathena
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Constraint(validatedBy = Enumeration.Validator.class)
public @interface Enumeration {

	/**
	 * 枚举类
	 */
	Class<? extends Enum<?>> enumClass();
	/**
	 * 由枚举本身提供的验证参数是否合法的方法
	 * 注解字段的类型需要和指定枚举内的isValid方法的参数类型相同
	 * 该方法必须是static的且返回值类型必须是Boolean/boolean
	 *
	 * Demo:
	 * public static boolean isValid(String channel) {
	 * 	   if (StringUtils.isEmpty(channel)) {
	 * 		   return false;
	 *     }
	 * 	   return Arrays.stream(DemoChannelEnum.values()).map(Enum::name).collect(Collectors.toSet()).contains(channel);
	 * }
	 */
	String enumMethod() default "isValid";
	/**
	 * 是否允许空参数通过校验
	 */
	boolean allowNull() default false;

	// ----------

	String message() default "value must be member of the specified enum";

	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

	// ----------

	class Validator implements ConstraintValidator<Enumeration, Object> {

		private Class<? extends Enum<?>> enumClass;
		private String enumMethodName;
		private Boolean allowNull;

		@Override
		public void initialize(Enumeration constraintAnnotation) {
			enumClass = constraintAnnotation.enumClass();
			enumMethodName = constraintAnnotation.enumMethod();
			allowNull = constraintAnnotation.allowNull();
		}

		@Override
		public boolean isValid(Object value, ConstraintValidatorContext context) {
			if (null == value) {
				return allowNull;
			}
			if (null == enumClass || null == enumMethodName) {
				return true;
			}
			Class<?> fieldClass = value.getClass();
			try {
				// @Enumeration 注解字段的类型需要和指定枚举内的 isValid() 方法的参数类型相同
				Method method = enumClass.getMethod(enumMethodName, fieldClass);
				if (!Boolean.TYPE.equals(method.getReturnType()) && !Boolean.class.equals(method.getReturnType())) {
					throw new ServiceException(String.format("return of the method %s is not boolean type in the enum %s", enumMethodName, enumClass));
				}
				if (!Modifier.isStatic(method.getModifiers())) {
					throw new ServiceException(String.format("the method %s is not static method in the enum %s", enumMethodName, enumClass));
				}
				Boolean result = (Boolean) method.invoke(null, value);
				return result == null ? false : result;
			} catch (NoSuchMethodException e) {
				throw new ServiceException(String.format("the method %s(%s) is not exist in the enum %s", enumMethodName, fieldClass, enumClass));
			} catch (IllegalAccessException | InvocationTargetException e) {
				throw new ServiceException(e);
			}
		}
	}

}