package com.mrathena.web.aspect.toolkit;

import com.mrathena.common.toolkit.IdKit;
import com.mrathena.common.toolkit.LogKit;
import com.mrathena.spring.boot.starter.api.business.BaseReqDTO;
import com.mrathena.spring.boot.starter.api.verify.Verifiable;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.StringJoiner;

/**
 * @author mrathena on 2020-03-29 02:26
 */
public final class AspectKit {

	private AspectKit() {}

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
		Object[] args = point.getArgs();
		if (ArrayUtils.isEmpty(args)) {
			return null;
		}
		StringJoiner joiner = new StringJoiner(", ");
		for (Object arg : args) {
			joiner.add(arg.toString());
		}
		return joiner.toString();
	}

	public static String getResponseStr(Object response) {
		try {
			Method method = response.getClass().getMethod("getResult");
			method.setAccessible(true);
			Object result = method.invoke(response);
			if (result instanceof Collection || result instanceof Map) {
				return "no print";
			} else {
				return response.toString();
			}
		} catch (Exception e) {
			return response.toString();
		}
	}

}
