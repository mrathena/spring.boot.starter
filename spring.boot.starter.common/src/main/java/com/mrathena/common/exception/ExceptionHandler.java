package com.mrathena.common.exception;

import com.mrathena.common.constant.Constant;
import com.mrathena.common.entity.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.dubbo.rpc.RpcException;

/**
 * @author com.mrathena on 2019/5/27 10:47
 */
@Slf4j
public final class ExceptionHandler {

	private ExceptionHandler() {}

	/**
	 * 获取异常的堆栈信息
	 */
	public static String getStackTraceStr(Throwable cause) {
		return ExceptionUtils.getStackTrace(cause);
	}

	/**
	 * 获取根源异常
	 */
	public static Throwable getRootCauseStackTrace(Throwable cause) {
		return ExceptionUtils.getRootCause(cause);
	}

	/**
	 * 获取根源异常的堆栈信息
	 */
	public static String getRootCauseStackTraceStr(Throwable cause) {
		return ExceptionUtils.getStackTrace(ExceptionUtils.getRootCause(cause));
	}

	/**
	 * 获取根源异常的类名和信息和描述
	 */
	public static String getClassInfoDescriptionIfPresent(Throwable cause) {
		Throwable rootCause = getRootCauseStackTrace(cause);
		if (rootCause instanceof IllegalArgumentException) {
			String info = rootCause.getClass().getName();
			return info + Constant.BLANK + Constant.COLON + Constant.BLANK + cause.getMessage();
		} else if (rootCause instanceof ServiceException) {
			ServiceException exception = (ServiceException) rootCause;
			String info = rootCause.getClass().getName();
			info += Constant.BLANK + Constant.COLON + Constant.BLANK + exception.getInfo();
			info += Constant.BLANK + Constant.COLON + Constant.BLANK + exception.getDescription();
			return info + System.lineSeparator() + getStackTraceStr(cause);
		} else if (rootCause instanceof BusinessException) {
			BusinessException exception = (BusinessException) rootCause;
			String info = rootCause.getClass().getName();
			info += Constant.BLANK + Constant.COLON + Constant.BLANK + exception.getInfo();
			info += Constant.BLANK + Constant.COLON + Constant.BLANK + exception.getDescription();
			return info + System.lineSeparator() + getStackTraceStr(cause);
		} else {
			return getStackTraceStr(cause);
		}
	}

	/**
	 * 统一处理异常
	 */
	public static <T> Response<T> getResponseFromThrowable(Throwable cause) {
		if (cause instanceof IllegalArgumentException) {
			return new Response<>(ErrorCodeEnum.ILLEGAL_ARGUMENT.name(), cause.getMessage());
		} else if (cause instanceof ServiceException) {
			ServiceException exception = (ServiceException) cause;
			return new Response<>(exception.getCode(), exception.getInfo());
		} else if (cause instanceof BusinessException) {
			BusinessException exception = (BusinessException) cause;
			return new Response<>(exception.getCode(), exception.getInfo());
		} else if (cause instanceof Exception) {
			return new Response<>(ErrorCodeEnum.EXCEPTION);
		} else {
			return new Response<>(ErrorCodeEnum.ERROR);
		}
	}

	/**
	 * 是否为常规阻断异常
	 */
	public static boolean isNormalBlockingException(Throwable cause) {
		return cause instanceof IllegalArgumentException || cause instanceof BusinessException;
	}

	/**
	 * 是否为 dubbo 的 no provider 异常
	 */
	public static boolean isDubboUnavailableException(Throwable cause) {
		if (cause instanceof RpcException) {
			return cause.getMessage().contains("No provider available");
		}
		return false;
	}

	/**
	 * 是否为 dubbo 的 timeout 异常
	 */
	public static boolean isDubboTimeoutException(Throwable cause) {
		if (cause instanceof RpcException) {
			RpcException rpcException = (RpcException) cause;
			return rpcException.isTimeout();
		}
		return false;
	}

}
