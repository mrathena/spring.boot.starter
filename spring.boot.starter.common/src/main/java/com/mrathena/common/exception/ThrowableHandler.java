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
public final class ThrowableHandler {

	private ThrowableHandler() {}

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
	 * 获取根源异常的类名和信息和描述, 长度有可能会很长
	 *
	 * IllegalArgumentException: 返回 IllegalArgumentException:message
	 * ServiceException: 如果是自己抛的, 返回 ServiceException:info:description, 如果是抓的, 返回 堆栈跟踪
	 * BusinessException: 一定是自己抛的, 返回 BusinessException:info:description
	 * 其他错误或异常: rootCause.getClass().getSimpleName():message
	 */
	public static String getClassInfoDescriptionIfPresent(Throwable cause) {
		Throwable rootCause = getRootCauseStackTrace(cause);
		if (rootCause instanceof ServiceException) {
			ServiceException exception = (ServiceException) rootCause;
			String info = rootCause.getClass().getSimpleName();
			info += Constant.COLON + exception.getCode();
			info += Constant.COLON + exception.getInfo();
			return info + Constant.COLON + exception.getDescription();
		} else if (rootCause instanceof BusinessException) {
			BusinessException exception = (BusinessException) rootCause;
			String info = rootCause.getClass().getSimpleName();
			info += Constant.COLON + exception.getCode();
			info += Constant.COLON + exception.getInfo();
			return info + Constant.COLON + exception.getDescription();
		} else {
			String info = rootCause.getClass().getSimpleName();
			return info + Constant.COLON + cause.getMessage();
		}
	}

	/**
	 * 统一处理异常
	 */
	public static <T> Response<T> getResponseFromThrowable(Throwable cause) {
		if (cause instanceof ServiceException) {
			ServiceException exception = (ServiceException) cause;
			return new Response<>(exception.getCode(), exception.getInfo());
		} else if (cause instanceof BusinessException) {
			BusinessException exception = (BusinessException) cause;
			return new Response<>(exception.getCode(), exception.getInfo());
		} else if (cause instanceof Exception) {
			return new Response<>(ServiceErrorCodeEnum.EXCEPTION);
		} else {
			return new Response<>(ServiceErrorCodeEnum.ERROR);
		}
	}

	/**
	 * 是否为常规阻断异常(可忽略异常)
	 */
	public static boolean isNegligibleException(Throwable cause) {
		return cause instanceof BusinessException;
	}

	/**
	 * 获取Throwable最终状态
	 */
	public static String getFinalThrowableStatus(Throwable cause) {
		if (cause instanceof BusinessException) {
			return Constant.SUCCESS;
		} else if (cause instanceof Exception) {
			return Constant.EXCEPTION;
		} else {
			return Constant.ERROR;
		}
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
