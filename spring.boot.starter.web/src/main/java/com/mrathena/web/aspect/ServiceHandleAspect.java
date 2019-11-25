package com.mrathena.web.aspect;

import com.mrathena.common.constant.Constant;
import com.mrathena.common.entity.Response;
import com.mrathena.common.exception.ExceptionHandler;
import com.mrathena.common.toolkit.IdKit;
import com.mrathena.common.toolkit.LogKit;
import com.mrathena.spring.boot.starter.api.BaseReqDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
	public Object around(ProceedingJoinPoint point) {
		LogKit.setTraceNo(getTraceNo(point));
		long begin = System.currentTimeMillis();
		String request = getRequestStr(point);
		try {
			log.info("REQUEST:{}", request);
			Object response = point.proceed();
			long interval = System.currentTimeMillis() - begin;
			// 日志的两个状态分别是 接口调用状态 和 交易状态
			// 1.[SUCCESS][SUCCESS]
			// 2.[SUCCESS][code:message], 报常规阻断式异常(ServiceException/IllegalArgumentException), 当作是接口调用成功
			// 3.[EXCEPTION][NONE], 报非常规阻断式异常(如SQLSyntaxErrorException等), 当作是接口调用失败
			log.info("[{}ms][SUCCESS][SUCCESS] REQUEST:{} RESPONSE:{}", interval, request, response);
			return response;
		} catch (Throwable throwable) {
			Response response = ExceptionHandler.handleBizException(throwable);
			long interval = System.currentTimeMillis() - begin;
			String invokeStatus = ExceptionHandler.isNormalBlockingException(throwable) ? Constant.SUCCESS : Constant.EXCEPTION;
			String tradeStatus = getTradeStatus(response);
			String message = ExceptionHandler.getRootCauseClassMessage(throwable);
			log.info("[{}ms][{}][{}] REQUEST:{} EXCEPTION:{}", interval, invokeStatus, tradeStatus, request, message);
			log.error("[{}ms][{}][{}] REQUEST:{} EXCEPTION:", interval, invokeStatus, tradeStatus, request, throwable);
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

	private String getTradeStatus(Response response) {
		String code = response.getCode();
		String message = response.getMessage();
		code = StringUtils.isBlank(code) ? Constant.EMPTY : code;
		message = StringUtils.isBlank(message) ? Constant.EMPTY : message;
		String codeMessage = code + Constant.COLON + message;
		return Constant.COLON.equals(codeMessage) ? Constant.NONE : codeMessage;
	}

}
