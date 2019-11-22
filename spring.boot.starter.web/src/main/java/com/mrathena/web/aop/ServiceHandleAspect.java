package com.mrathena.web.aop;

import com.mrathena.common.constant.Constant;
import com.mrathena.common.entity.Response;
import com.mrathena.common.exception.ExceptionHandler;
import com.mrathena.common.toolkit.IdKit;
import com.mrathena.common.toolkit.LogKit;
import com.mrathena.spring.boot.starter.api.BaseReqDTO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author mrathena on 2019/11/22 14:26
 */
@Slf4j
@Aspect
@Component
public class ServiceHandleAspect {

	@Around("execution (* com.mrathena.service..*Impl.*(..))")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		LogKit.setTraceNo(getTraceNo(point));
		long begin = System.currentTimeMillis();
		try {
			log.info("REQUEST:{}", getRequestStr(point));
			Object response = point.proceed();
			long interval = System.currentTimeMillis() - begin;
			log.info("[{}ms][SUCCESS][SUCCESS] REQUEST:{} RESPONSE:{}", interval, getRequestStr(point), response);
			return response;
		} catch (Exception e) {
			Response response = ExceptionHandler.handleBizException(e);
			long interval = System.currentTimeMillis() - begin;
			String status = ExceptionHandler.isNormalBlockingException(e) ? Constant.SUCCESS : Constant.EXCEPTION;
			String codeMsg = response.getCode() + Constant.COLON + response.getMessage();
			String message = ExceptionHandler.getStackTrace(e);
			log.info("[{}ms][{}][{}] REQUEST:{} EXCEPTION:{}", interval, status, codeMsg, getRequestStr(point), message);
			log.error("[{}ms][{}][{}] REQUEST:{} EXCEPTION:", interval, status, codeMsg, getRequestStr(point), e);
			return response;
		} finally {
			MDC.clear();
		}
	}

	private String getTraceNo(ProceedingJoinPoint point) {
		for (Object arg : point.getArgs()) {
			if (arg instanceof BaseReqDTO) {
				return ((BaseReqDTO) arg).getTraceNo();
			}
		}
		return IdKit.getUuid();
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

}
