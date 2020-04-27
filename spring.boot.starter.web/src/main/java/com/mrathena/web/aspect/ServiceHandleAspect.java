package com.mrathena.web.aspect;

import com.mrathena.common.constant.Constant;
import com.mrathena.common.entity.Response;
import com.mrathena.common.exception.ThrowableHandler;
import com.mrathena.web.aspect.toolkit.AspectKit;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

/**
 * @author mrathena on 2019/11/22 14:26
 */
@Aspect
@Component
public class ServiceHandleAspect {

	@Around("execution (* com.mrathena.service..*Impl.*(..))")
	public Object around(ProceedingJoinPoint point) {
		long begin = System.currentTimeMillis();
		AspectKit.setLogTraceNo(point);
		Logger log = AspectKit.getLogger(point);
		String request = AspectKit.getRequestStr(point);
		try {
			AspectKit.checkRequest(point);
			AspectKit.setLogClassNameAndMethodName(point);
			log.info("[PARAMETER:{}]", request);
			AspectKit.removeLogClassNameAndMethodName();
			Object response = point.proceed();
			long interval = System.currentTimeMillis() - begin;
			AspectKit.setLogClassNameAndMethodName(point);
			log.info("[{}ms][SUCCESS][REQUEST:{}][RESPONSE:{}]", interval, request, AspectKit.getResponseStr(response));
			AspectKit.removeLogClassNameAndMethodName();
			return response;
		} catch (Throwable cause) {
			Response<?> response = ThrowableHandler.getResponseFromThrowable(cause);
			long interval = System.currentTimeMillis() - begin;
			String status = ThrowableHandler.isNormalBlockingException(cause) ? Constant.SUCCESS : Constant.EXCEPTION;
			String message = ThrowableHandler.getClassInfoDescriptionIfPresent(cause);
			AspectKit.setLogClassNameAndMethodName(point);
			log.info("[{}ms][{}][REQUEST:{}][RESPONSE:{}][EXCEPTION:{}]", interval, status, request, AspectKit.getResponseStr(response), message);
			if (!ThrowableHandler.isNormalBlockingException(cause)) {
				log.error("[{}ms][{}][REQUEST:{}][RESPONSE:{}][EXCEPTION:{}]", interval, status, request, AspectKit.getResponseStr(response), message);
			}
			AspectKit.removeLogClassNameAndMethodName();
			return response;
		} finally {
			MDC.clear();
		}
	}

}
