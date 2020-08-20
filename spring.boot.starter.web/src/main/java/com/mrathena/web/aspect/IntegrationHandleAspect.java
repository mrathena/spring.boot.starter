package com.mrathena.web.aspect;

import com.mrathena.common.exception.ServiceErrorCodeEnum;
import com.mrathena.common.exception.ThrowableHandler;
import com.mrathena.web.aspect.toolkit.AspectKit;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

/**
 * @author mrathena on 2019/11/25 10:18
 */
@Aspect
@Component
public class IntegrationHandleAspect {

	@Around("execution (* com.mrathena.remote..*Integration.*(..))")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long begin = System.currentTimeMillis();
		Logger log = AspectKit.getLogger(point);
		Object request = AspectKit.getRequest(point);
		try {
			AspectKit.setLogClassNameAndMethodName(point);
			log.info("[REMOTE:REQUEST:{}]", request);
			AspectKit.removeLogClassNameAndMethodName();
			Object response = point.proceed();
			long interval = System.currentTimeMillis() - begin;
			AspectKit.setLogClassNameAndMethodName(point);
			log.info("[{}ms][SUCCESS][REMOTE:REQUEST:{}][REMOTE:RESPONSE:{}]", interval, request, AspectKit.getResponse(response));
			AspectKit.removeLogClassNameAndMethodName();
			return response;
		} catch (Throwable cause) {
			long interval = System.currentTimeMillis() - begin;
			String status = getExceptionCause(cause);
			String message = ThrowableHandler.getStackTraceStr(cause);
			AspectKit.setLogClassNameAndMethodName(point);
			log.info("[{}][{}][REMOTE:REQUEST:{}][REMOTE:THROWABLE:{}]", interval, status, request, message);
			log.error("[{}][{}][REMOTE:REQUEST:{}][REMOTE:THROWABLE:{}]", interval, status, request, message, cause);
			AspectKit.removeLogClassNameAndMethodName();
			throw cause;
		}
	}

	/**
	 * 获取异常原因
	 */
	private String getExceptionCause(Throwable cause) {
		if (ThrowableHandler.isDubboUnavailableException(cause)) {
			return ServiceErrorCodeEnum.REMOTE_SERVICE_UNAVAILABLE.name();
		} else if (ThrowableHandler.isDubboTimeoutException(cause)) {
			return ServiceErrorCodeEnum.REMOTE_SERVICE_INVOKE_TIMEOUT.name();
		}
		return ServiceErrorCodeEnum.REMOTE_SERVICE_INVOKE_FAILURE.name();
	}

}
