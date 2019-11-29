package com.mrathena.web.aspect;

import com.bestpay.basic.service.response.BasicResponse;
import com.bestpay.dubbo.result.Result;
import com.mrathena.common.constant.Constant;
import com.mrathena.common.exception.ExceptionHandler;
import com.mrathena.common.toolkit.LogKit;
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
		Logger log = AspectKit.Around.getLogger(point);
		LogKit.setClassName(AspectKit.Around.getClassName(point));
		LogKit.setMethodName(AspectKit.Around.getMethodName(point));
		String request = AspectKit.Around.getRequestStr(point);
		try {
			log.info("REMOTE:REQUEST:{}", request);
			Object response = point.proceed();
			long interval = System.currentTimeMillis() - begin;
			String status = getTradeStatus(response);
			log.info("[{}ms][SUCCESS][{}] REMOTE:REQUEST:{} REMOTE:RESPONSE:{}", interval, status, request, response);
			return response;
		} catch (Throwable throwable) {
			long interval = System.currentTimeMillis() - begin;
			String message = ExceptionHandler.getStackTraceStr(throwable);
			log.info("[{}ms][EXCEPTION][NONE] REMOTE:REQUEST:{} REMOTE:RESPONSE:{}", interval, request, message);
			log.error("[{}ms][EXCEPTION][NONE] REMOTE:REQUEST:{} REMOTE:RESPONSE:", interval, request, throwable);
			throw throwable;
		}
	}

	/**
	 * 有其他类型要添加
	 */
	private String getTradeStatus(Object object) {
		if (object instanceof Result) {
			Result response = (Result) object;
			return response.isSuccess() ? Constant.SUCCESS : response.getErrorCode() + Constant.COLON + response.getErrorMsg();
		} else if (object instanceof BasicResponse) {
			BasicResponse response = (BasicResponse) object;
			return response.isSuccess() ? Constant.SUCCESS : response.getErrorCode() + Constant.COLON + response.getErrorMsg();
		}
		return "UNKNOWN";
	}

}
