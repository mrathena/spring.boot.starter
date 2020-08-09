package com.mrathena.web.aspect.toolkit;

import com.mrathena.common.toolkit.IdKit;
import com.mrathena.common.toolkit.LogKit;
import com.mrathena.spring.boot.starter.api.business.BaseReqDTO;
import com.mrathena.spring.boot.starter.api.verify.Verifiable;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author mrathena on 2020-03-29 02:26
 */
@Slf4j
public final class AspectKit {

	private AspectKit() {}

	private static final List<Class<?>> CLASS_LIST = Arrays.asList(Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Boolean.class, Character.class,
			byte.class, short.class, int.class, long.class, float.class, double.class, boolean.class, char.class,
			String.class, Date.class, LocalDateTime.class, LocalDate.class, LocalTime.class);

	public static Logger getLogger(ProceedingJoinPoint point) {
		Class<?> clazz = point.getTarget().getClass();
		try {
			Field log = clazz.getDeclaredField("log");
			log.setAccessible(true);
			return (Logger) log.get(null);
		} catch (Exception e) {
			return LoggerFactory.getLogger(clazz);
		}
	}

	public static void setLogClassNameAndMethodName(ProceedingJoinPoint point) {
		String className = point.getTarget().getClass().getSimpleName();
		String methodName = point.getSignature().getName();
		LogKit.setClassNameAndMethodName(className, methodName);
	}

	public static void removeLogClassNameAndMethodName() {
		LogKit.removeClassNameAndMethodName();
	}

	public static void setLogTraceNo(ProceedingJoinPoint point) {
		for (Object arg : point.getArgs()) {
			if (arg instanceof BaseReqDTO) {
				LogKit.setTraceNo(((BaseReqDTO) arg).getTraceNo());
				return;
			}
		}
		LogKit.setTraceNo(IdKit.getUuid());
	}

	public static void checkRequest(ProceedingJoinPoint point) {
		for (Object arg : point.getArgs()) {
			if (arg instanceof Verifiable) {
				Verifiable request = (Verifiable) arg;
				request.verify();
			}
		}
	}

	public static String getRequestStr(ProceedingJoinPoint point) {
		try {
			Object[] args = point.getArgs();
			if (ArrayUtils.isEmpty(args)) {
				return null;
			}
			return Arrays.stream(args).map(AspectKit::getAllFieldNameAndValueStr).collect(Collectors.joining(";"));
		} catch (Throwable cause) {
			return "UNKNOWN";
		}
	}

	private static String getAllFieldNameAndValueStr(Object object) {
		if (null == object) {
			return "null";
		}
		Class<?> clazz = object.getClass();
		if (CLASS_LIST.contains(clazz)) {
			return String.valueOf(object);
		}
		String simpleTypeName = clazz.getTypeName().substring(clazz.getTypeName().lastIndexOf(".")).replace(".", "");
		Map<String, Object> fieldMap = new HashMap<>();
		try {
			//获取本身和父级对象
			for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
				//获取所有字段
				Field[] fields = clazz.getDeclaredFields();
				for (Field field : fields) {
					field.setAccessible(true);
					String fieldName = field.getName();
					if ("serialVersionUID".equals(fieldName)) {
						continue;
					}
					if (null == fieldMap.get(field.getName())) {
						fieldMap.put(fieldName, field.get(object));
					}
				}
			}
			String collect = fieldMap.entrySet().stream().filter(entry -> entry.getValue() != null).map(entry -> entry.getKey() + "=" + entry.getValue()).collect(Collectors.joining(","));
			return simpleTypeName + "(" + collect + ")";
		} catch (Throwable cause) {
			return null;
		}
	}

	public static String getResponseStr(Object object) {
		try {
			Class<?> clazz = object.getClass();
			if (CLASS_LIST.contains(clazz)) {
				return String.valueOf(object);
			}
			String simpleClassName = clazz.getSimpleName();
			Field[] fields = clazz.getDeclaredFields();
			Map<String, Object> fieldMap = new HashMap<>();
			for (Field field : fields) {
				field.setAccessible(true);
				String fieldName = field.getName();
				Object fieldValue = field.get(object);
				if ("serialVersionUID".equals(fieldName) || null == fieldValue) {
					continue;
				}
				if ("result".equals(fieldName)) {
					if ((fieldValue instanceof Collection) || fieldValue instanceof Map) {
						fieldValue = "Collection or Map";
					}
				}
				fieldMap.put(fieldName, fieldValue);
			}
			String collect = fieldMap.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue()).collect(Collectors.joining(","));
			return simpleClassName + "(" + collect + ")";
		} catch (Throwable cause) {
			return "UNKNOWN";
		}
	}

}
