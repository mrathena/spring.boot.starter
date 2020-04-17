package com.mrathena.web.aspect;

import com.mrathena.common.constant.Constant;
import com.mrathena.common.entity.Response;
import com.mrathena.common.exception.ExceptionHandler;
import com.mrathena.web.aspect.toolkit.AspectKit;
import org.apache.commons.lang3.StringUtils;
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
			log.info("REQUEST:{}", request);
			AspectKit.removeLogClassNameAndMethodName();
			Object response = point.proceed();
			long interval = System.currentTimeMillis() - begin;
			// 日志的两个状态分别是 接口调用状态 和 交易状态
			// 1.[SUCCESS][SUCCESS]
			// 2.[SUCCESS][code:message], 报常规阻断式异常(BusinessException/IllegalArgumentException), 当作是接口调用成功
			// 3.[EXCEPTION][NONE], 报非常规阻断式异常(ServiceException/SQLSyntaxErrorException等), 当作是接口调用失败
			AspectKit.setLogClassNameAndMethodName(point);
			log.info("[{}ms][SUCCESS][SUCCESS] PARAMETER:{} RESPONSE:{}", interval, request, AspectKit.getResponseStr(response));
			AspectKit.removeLogClassNameAndMethodName();
			return response;
		} catch (Throwable cause) {
			Response response = ExceptionHandler.getResponseFromThrowable(cause);
			long interval = System.currentTimeMillis() - begin;
			String invokeStatus = ExceptionHandler.isNormalBlockingException(cause) ? Constant.SUCCESS : Constant.EXCEPTION;
			String tradeStatus = getTradeStatus(response);
			String message = ExceptionHandler.getClassInfoDescriptionIfPresent(cause);
			AspectKit.setLogClassNameAndMethodName(point);
			log.info("[{}ms][{}][{}] PARAMETER:{} EXCEPTION:{}", interval, invokeStatus, tradeStatus, request, message);
			log.error("[{}ms][{}][{}] PARAMETER:{} EXCEPTION:", interval, invokeStatus, tradeStatus, request, cause);
			AspectKit.removeLogClassNameAndMethodName();
			return response;
		} finally {
			MDC.clear();
		}
	}

	private String getTradeStatus(Response response) {
		String code = response.getCode();
		String message = response.getMessage();
		code = StringUtils.isBlank(code) ? Constant.EMPTY : code;
		message = StringUtils.isBlank(message) ? Constant.EMPTY : message;
		String codeMessage = code + Constant.COLON + message;
		return Constant.COLON.equals(codeMessage) ? Constant.UNKNOWN : codeMessage;
	}

}
