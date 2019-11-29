package com.mrathena.web.aspect.toolkit;

import com.mrathena.common.toolkit.IdKit;
import com.mrathena.spring.boot.starter.api.BaseReqDTO;
import com.mrathena.spring.boot.starter.toolkit.verify.Verifiable;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author mrathena on 2019/11/29 15:20
 */
public final class AspectKit {

	private AspectKit() {}

	/**
	 * 环绕通知
	 */
	public static class Around {

		public static Logger getLogger(ProceedingJoinPoint point) {
			Class<?> clazz = point.getTarget().getClass();
			Logger logger;
			try {
				Field log = clazz.getDeclaredField("log");
				log.setAccessible(true);
				return (Logger) log.get(null);
			} catch (Exception e) {
				return LoggerFactory.getLogger(clazz);
			}
		}

		public static String getClassName(ProceedingJoinPoint point) {
			return point.getTarget().getClass().getSimpleName();
		}

		public static String getMethodName(ProceedingJoinPoint point) {
			return point.getSignature().getName();
		}

		public static void checkRequest(ProceedingJoinPoint point) {
			for (Object arg : point.getArgs()) {
				if (arg instanceof Verifiable) {
					Verifiable request = (Verifiable) arg;
					request.verify();
				}
			}
		}

		public static String getTraceNo(ProceedingJoinPoint point) {
			for (Object arg : point.getArgs()) {
				if (arg instanceof BaseReqDTO) {
					return ((BaseReqDTO) arg).getTraceNo();
				}
			}
			return IdKit.getUuid();
		}

		public static String getRequestStr(ProceedingJoinPoint point) {
			Object[] args = point.getArgs();
			if (Objects.isNull(args) || args.length == 0) {
				return null;
			}
			StringJoiner joiner = new StringJoiner(", ");
			for (Object arg : args) {
				joiner.add(arg.toString());
			}
			return joiner.toString();
		}

	}

}
