package com.mrathena.spring.boot.starter.toolkit.verify;

import com.mrathena.spring.boot.starter.api.y2019.demo.CreateDemoReqDTO;
import com.mrathena.spring.boot.starter.toolkit.verify.annotation.MemberOf;
import com.mrathena.spring.boot.starter.toolkit.verify.annotation.NotEmpty;
import com.mrathena.spring.boot.starter.toolkit.verify.annotation.NotNull;
import com.mrathena.spring.boot.starter.toolkit.verify.annotation.ProductNo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author mrathena on 2019/11/14 14:12
 */
public final class VerifyKit {

	public static void main(String[] args) {
		CreateDemoReqDTO request = new CreateDemoReqDTO();
		request.setTraceNo(null);
		request.setDemo("");
		request.setProductNo("18234089811");
		request.setChannel("APP");
		request.verify();
	}

	private VerifyKit() {}

	private static final Map<Class, Verifier> STATISTICS = new HashMap<>();
	private static final String MESSAGE = "message";
	private static final Pattern PATTERN_PRODUCT_NO = Pattern.compile("1\\d{10}");

	static {
		STATISTICS.put(NotNull.class, (field, value, set) -> {
			if (isNull(value)) {
				set.add(getMessage(field, NotNull.class));
			}
		});
		STATISTICS.put(NotEmpty.class, (field, value, set) -> {
			if (isNullOrEmpty(field, value)) {
				set.add(getMessage(field, NotEmpty.class));
			}
		});
		STATISTICS.put(ProductNo.class, (field, value, set) -> {
			if (!isProductNo(field, value)) {
				set.add(getMessage(field, ProductNo.class));
			}
		});
		STATISTICS.put(MemberOf.class, (field, value, set) -> {
			try {
				if (!isMemberOf(field, value)) {
					MemberOf annotation = field.getAnnotation(MemberOf.class);
					String[] members = annotation.members();
					String currentMessage = annotation.message();
					Object defaultMessage = annotation.getClass().getMethod(MESSAGE).getDefaultValue();

					set.add(getMessage(field, MemberOf.class));
				}
			} catch (Exception e) {

			}
		});
	}

	@SuppressWarnings("unchecked")
	private static String getMessage(Field field, Class annotationClass) {
		try {
			Method method = annotationClass.getMethod(MESSAGE);
			Object defaultMessage = method.getDefaultValue();
			Object currentMessage = method.invoke(field.getAnnotation(annotationClass));
			if (currentMessage.equals(defaultMessage)) {
				return field.getName() + " " + defaultMessage;
			} else {
				return currentMessage.toString();
			}
		} catch (Exception e) {
			return field.getName() + " 非法";
		}
	}

	private static boolean isNull(Object value) {
		return null == value;
	}

	private static boolean isNullOrEmpty(Field field, Object value) {
		if (isNull(value)) {
			return true;
		}
		Class fieldClass = field.getType();
		if (String.class == fieldClass) {
			return ((String) value).isEmpty();
		}
		return false;
	}

	private static boolean isProductNo(Field field, Object value) {
		if (isNullOrEmpty(field, value)) {
			return false;
		}
		Matcher matcher = PATTERN_PRODUCT_NO.matcher(value.toString());
		return matcher.matches();
	}

	private static boolean isMemberOf(Field field, Object value) {
		if (isNullOrEmpty(field, value)) {
			return false;
		}
		String[] members = field.getAnnotation(MemberOf.class).members();
		return Arrays.asList(members).contains(value.toString());
	}

	/**
	 * 参数验证
	 */
	public static void verify(Object parameter) {
		try {
			// 存储本类及父类中所有申明了参数验证注解的字段
			Stack<Field> fieldStack = new Stack<>();
			// 获取本类注解字段
			Class<?> clazz = parameter.getClass();
			Field[] declaredFieldArray = clazz.getDeclaredFields();
			for (Field field : declaredFieldArray) {
				Annotation[] declaredAnnotationArray = field.getDeclaredAnnotations();
				if (null != declaredAnnotationArray && declaredAnnotationArray.length > 0) {
					fieldStack.push(field);
				}
			}
			// 获取父类注解字段
			Class<?> superClass = clazz.getSuperclass();
			while (Object.class != superClass) {
				for (Field field : superClass.getDeclaredFields()) {
					Annotation[] declaredAnnotationArray = field.getDeclaredAnnotations();
					if (null != declaredAnnotationArray && declaredAnnotationArray.length > 0) {
						fieldStack.push(field);
					}
				}
				superClass = superClass.getSuperclass();
			}
			// 按后进先出顺序遍历并校验字段
			Set<String> set = new HashSet<>();
			while (!fieldStack.isEmpty()) {
				Field field = fieldStack.pop();
				field.setAccessible(true);
				Annotation[] declaredAnnotationArray = field.getDeclaredAnnotations();
				for (Annotation annotation : declaredAnnotationArray) {
					STATISTICS.get(annotation.annotationType()).verify(field, field.get(parameter), set);
				}
			}
			// 处理校验结果
			if (!set.isEmpty()) {
				StringJoiner description = new StringJoiner("; ");
				for (String string : set) {
					description.add(string);
				}
				throw new IllegalArgumentException(description.toString());
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}

interface Verifier {
	/**
	 * verify
	 *
	 * @param field .
	 * @param value .
	 * @param set   .
	 */
	void verify(Field field, Object value, Set<String> set);
}
