package com.mrathena.web.aspect;

import com.bestpay.basic.service.response.BasicResponse;
import com.bestpay.dubbo.result.Result;
import com.mrathena.common.constant.Constant;
import com.mrathena.common.exception.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author mrathena on 2019/11/25 10:18
 */
@Slf4j
@Component
public class IntegrationHandleAspect {

	@Around("execution (* com.mrathena.remote..*Integration.*(..))")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long begin = System.currentTimeMillis();
		String request = getRequestStr(point);
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
			log.info("[{}ms][EXCEPTION][NONE] REMOTE:REQUEST:{} REMOTE:EXCEPTION:{}", interval, request, message);
			log.error("[{}ms][EXCEPTION][NONE] REMOTE:REQUEST:{} REMOTE:EXCEPTION:", interval, request, throwable);
			throw throwable;
		}
	}

	private String getRequestStr(ProceedingJoinPoint point) {
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
