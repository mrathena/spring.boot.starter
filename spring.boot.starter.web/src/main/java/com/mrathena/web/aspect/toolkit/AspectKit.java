package com.mrathena.web.aspect.toolkit;

import com.mrathena.common.constant.Constant;
import com.mrathena.common.entity.Response;
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
import java.util.*;

/**
 * @author mrathena on 2020-03-29 02:26
 */
@Slf4j
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

	public static Object getRequest(ProceedingJoinPoint point) {
		try {
			Object[] args = point.getArgs();
			if (ArrayUtils.isEmpty(args)) {
				return null;
			}
			List<String> argList = new LinkedList<>();
			for (Object arg : args) {
				if (null != arg) {
					argList.add(arg.toString().replace(Constant.BLANK, Constant.EMPTY));
				}
			}
			return argList.size() == 1 ? argList.get(0) : argList;
		} catch (Throwable cause) {
			return Constant.ERROR;
		}
	}

	public static Object getResponse(Object object) {
		try {
			if (null == object) {
				return null;
			}
			if ((object instanceof Collection) || object instanceof Map) {
				return "CollectionOrMap";
			}
			if (object instanceof Response) {
				Class<?> clazz = object.getClass();
				Field[] fields = clazz.getDeclaredFields();
				Map<String, Object> fieldMap = new HashMap<>();
				for (Field field : fields) {
					field.setAccessible(true);
					String fieldName = field.getName();
					Object fieldValue = field.get(object);
					if ("serialVersionUID".equals(fieldName)) {
						continue;
					}
					if ("result".equals(fieldName)) {
						if ((fieldValue instanceof Collection) || fieldValue instanceof Map) {
							fieldValue = "CollectionOrMap";
						}
					}
					fieldMap.put(fieldName, fieldValue);
				}
				return fieldMap.toString().replace(Constant.BLANK, Constant.EMPTY);
			}
			return object;
		} catch (Throwable cause) {
			return Constant.ERROR;
		}
	}

}

