package com.mrathena.web.aspect;

import com.mrathena.common.constant.Constant;
import com.mrathena.common.entity.Response;
import com.mrathena.common.exception.ErrorCodeEnum;
import com.mrathena.common.exception.ExceptionHandler;
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
		String request = AspectKit.getRequestStr(point);
		try {
			AspectKit.setLogClassNameAndMethodName(point);
			log.info("REMOTE:REQUEST:{}", request);
			AspectKit.removeLogClassNameAndMethodName();
			Object response = point.proceed();
			long interval = System.currentTimeMillis() - begin;
			String status = getTradeStatus(response);
			AspectKit.setLogClassNameAndMethodName(point);
			log.info("[{}ms][SUCCESS][{}] REMOTE:PARAMETER:{} REMOTE:RESPONSE:{}", interval, status, request, AspectKit.getResponseStr(response));
			AspectKit.removeLogClassNameAndMethodName();
			return response;
		} catch (Throwable cause) {
			long interval = System.currentTimeMillis() - begin;
			String status = getExceptionCause(cause);
			String message = ExceptionHandler.getStackTraceStr(cause);
			AspectKit.setLogClassNameAndMethodName(point);
			log.info("[{}ms][EXCEPTION][{}] REMOTE:PARAMETER:{} REMOTE:EXCEPTION:{}", interval, status, request, message);
			log.error("[{}ms][EXCEPTION][{}] REMOTE:PARAMETER:{} REMOTE:EXCEPTION:", interval, status, request, cause);
			AspectKit.removeLogClassNameAndMethodName();
			throw cause;
		}
	}

	/**
	 * 获取交易状态
	 *
	 * 有其他类型的返回值的话, 要添加在这里
	 */
	private String getTradeStatus(Object object) {
		if (object instanceof Response) {
			Response response = (Response) object;
			return response.isSuccess() ? Constant.SUCCESS : response.getCode() + Constant.COLON + response.getMessage();
		}
		return Constant.UNKNOWN;
	}

	/**
	 * 获取异常原因
	 */
	private String getExceptionCause(Throwable throwable) {
		if (ExceptionHandler.isDubboUnavailableException(throwable)) {
			return ErrorCodeEnum.REMOTE_SERVICE_UNAVAILABLE.name();
		} else if (ExceptionHandler.isDubboTimeoutException(throwable)) {
			return ErrorCodeEnum.REMOTE_SERVICE_INVOKE_TIMEOUT.name();
		}
		return ErrorCodeEnum.REMOTE_SERVICE_INVOKE_FAILURE.name();
	}

}
