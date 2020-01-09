package com.mrathena.common.toolkit;

import com.mrathena.common.exception.ServiceException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射
 *
 * @author mrathena
 */
public final class ReflectionKit {

	private ReflectionKit() {}

	/**
	 * 通过反射取对象指定字段(属性)的值
	 *
	 * @param object    目标对象
	 * @param fieldName 字段的名字
	 * @return 字段的值
	 */
	public static Object getValue(Object object, String fieldName) {
		Class<?> clazz = object.getClass();
		String[] fs = fieldName.split("\\.");

		try {
			for (int i = 0; i < fs.length - 1; i++) {
				Field field = clazz.getDeclaredField(fs[i]);
				field.setAccessible(true);
				object = field.get(object);
				clazz = object.getClass();
			}

			Field field = clazz.getDeclaredField(fs[fs.length - 1]);
			field.setAccessible(true);
			return field.get(object);
		} catch (Throwable throwable) {
			throw new ServiceException(throwable);
		}
	}

	/**
	 * 通过反射给对象的指定字段赋值
	 *
	 * @param object    目标对象
	 * @param fieldName 字段的名称
	 * @param value     值
	 */
	public static void setValue(Object object, String fieldName, Object value) {
		Class<?> clazz = object.getClass();
		String[] fs = fieldName.split("\\.");
		try {
			for (int i = 0; i < fs.length - 1; i++) {
				Field field = clazz.getDeclaredField(fs[i]);
				field.setAccessible(true);
				Object val = field.get(object);
				if (val == null) {
					Constructor<?> c = field.getType().getDeclaredConstructor();
					c.setAccessible(true);
					val = c.newInstance();
					field.set(object, val);
				}
				object = val;
				clazz = object.getClass();
			}

			Field field = clazz.getDeclaredField(fs[fs.length - 1]);
			field.setAccessible(true);
			field.set(object, value);
		} catch (Throwable throwable) {
			throw new ServiceException(throwable);
		}
	}

	/**
	 * 调用指定方法
	 *
	 * @param object     目标对象
	 * @param methodName 方法名称
	 * @param parameters 参数
	 * @return .
	 */
	public static Object invoke(Object object, String methodName, Object... parameters) {
		Class<?> clazz = object.getClass();
		try {
			Class<?>[] parameterTypes = new Class[parameters.length];
			for (int i = 0; i < parameters.length; i++) {
				parameterTypes[i] = parameters.getClass();
			}
			Method method = clazz.getMethod(methodName, parameterTypes);
			method.setAccessible(true);
			return method.invoke(object, parameters);
		} catch (Throwable throwable) {
			throw new ServiceException(throwable);
		}
	}

}