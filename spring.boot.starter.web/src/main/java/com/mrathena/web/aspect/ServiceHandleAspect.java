package com.mrathena.web.aspect;

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
		Object request = AspectKit.getRequest(point);
		try {
			AspectKit.setLogClassNameAndMethodName(point);
			log.info("[REQUEST:{}]", request);
			AspectKit.removeLogClassNameAndMethodName();
			AspectKit.checkRequest(point);
			Object response = point.proceed();
			long interval = System.currentTimeMillis() - begin;
			AspectKit.setLogClassNameAndMethodName(point);
			log.info("[{}][SUCCESS][REQUEST:{}][RESPONSE:{}]", interval, request, AspectKit.getResponse(response));
			AspectKit.removeLogClassNameAndMethodName();
			return response;
		} catch (Throwable cause) {
			Response<?> response = ThrowableHandler.getResponseFromThrowable(cause);
			long interval = System.currentTimeMillis() - begin;
			String status = ThrowableHandler.getFinalThrowableStatus(cause);
			String message = ThrowableHandler.getClassInfoDescriptionIfPresent(cause);
			AspectKit.setLogClassNameAndMethodName(point);
			log.info("[{}][{}][REQUEST:{}][RESPONSE:{}][THROWABLE:{}]", interval, status, request, AspectKit.getResponse(response), message);
			if (!ThrowableHandler.isNegligibleException(cause)) {
				log.error("[{}][{}][REQUEST:{}][RESPONSE:{}][THROWABLE:{}]", interval, status, request, AspectKit.getResponse(response), message, cause);
			}
			AspectKit.removeLogClassNameAndMethodName();
			return response;
		} finally {
			MDC.clear();
		}
	}

}
