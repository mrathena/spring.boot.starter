package com.mrathena.common.exception;

import com.bestpay.dubbo.result.Result;
import com.mrathena.common.constant.Constant;
import com.mrathena.common.entity.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.dubbo.rpc.RpcException;

/**
 * @author mrathena on 2019/5/27 10:47
 */
@Slf4j
public final class ExceptionHandler {

	private ExceptionHandler() {}

	/**
	 * 获取异常的堆栈信息
	 */
	public static String getStackTraceStr(Throwable throwable) {
		return ExceptionUtils.getStackTrace(throwable);
	}

	/**
	 * 获取根源异常
	 */
	public static Throwable getRootCauseStackTrace(Throwable throwable) {
		return ExceptionUtils.getRootCause(throwable);
	}

	/**
	 * 获取根源异常的堆栈信息
	 */
	public static String getRootCauseStackTraceStr(Throwable throwable) {
		return ExceptionUtils.getStackTrace(ExceptionUtils.getRootCause(throwable));
	}

	/**
	 * 获取异常的类名和信息
	 * java.lang.NullPointerException: null
	 * com.mrathena.common.exception.ServiceException: 客户不存在
	 */
	public static String getClassMessage(Throwable throwable) {
		return throwable.getClass().getName() + Constant.COLON + Constant.BLANK + throwable.getMessage();
	}

	/**
	 * 获取根源异常的类名和信息
	 */
	public static String getRootCauseClassMessage(Throwable throwable) {
		return getClassMessage(getRootCauseStackTrace(throwable));
	}

	/**
	 * 获取异常描述信息
	 * 获取客户信息异常[java.lang.NullPointerException: null]
	 * 获取客户信息异常[com.mrathena.common.exception.ServiceException: 客户不存在]
	 */
	public static String getDescriptionClassMessage(Throwable throwable, String content) {
		return content + Constant.L_BRACKET + getClassMessage(throwable) + Constant.R_BRACKET;
	}

	/**
	 * 获取根源异常的类名和信息
	 */
	public static String getRootCauseDescriptionClassMessage(Throwable throwable, String content) {
		return getDescriptionClassMessage(getRootCauseStackTrace(throwable), content);
	}

	/**
	 * 判断异常是否为Dubbo的 timeout 异常
	 */
	public static boolean isDubboTimeoutException(Throwable throwable) {
		if (throwable instanceof RpcException) {
			RpcException rpcException = (RpcException) throwable;
			return rpcException.isTimeout();
		}
		return false;
	}

	/**
	 * 判断异常是否为Dubbo的 no provider available 异常
	 */
	public static boolean isDubboUnavailableException(Throwable throwable) {
		if (throwable instanceof RpcException) {
			RpcException rpcException = (RpcException) throwable;
			return rpcException.isNoInvokerAvailableAfterFilter();
		}
		return false;
	}

	/**
	 * 统一处理异常
	 * return ExceptionHandler.handleBizException(e);
	 */
	public static <T> Response<T> handleBizException(Exception exception) {
		Response<T> response = new Response<>();
		if (exception instanceof IllegalArgumentException) {
			response.setCode(ExceptionCodeEnum.ILLEGAL_ARGUMENT.name());
			response.setMessage(exception.getMessage());
		} else if (exception instanceof ServiceException) {
			ServiceException serviceException = (ServiceException) exception;
			response.setCode(serviceException.getCode());
			response.setMessage(serviceException.getMessage());
		} else if (exception instanceof RemoteServiceException) {
			RemoteServiceException remoteServiceException = (RemoteServiceException) exception;
			response.setCode(remoteServiceException.getCode());
			response.setMessage(remoteServiceException.getMessage());
		} else {
			response.setCode(ExceptionCodeEnum.EXCEPTION.name());
			response.setMessage(ExceptionCodeEnum.EXCEPTION.getMessage());
		}
		return response;
	}

	/**
	 * 统一处理异常
	 * return ExceptionHandler.handleBizException(e);
	 */
	public static <T> Result<T> handleBusinessException(Exception exception) {
		Result<T> response = new Result<>();
		if (exception instanceof IllegalArgumentException) {
			response.setErrorCode(ExceptionCodeEnum.ILLEGAL_ARGUMENT.name());
			response.setErrorMsg(exception.getMessage());
		} else if (exception instanceof ServiceException) {
			ServiceException serviceException = (ServiceException) exception;
			response.setErrorCode(serviceException.getCode());
			response.setErrorMsg(serviceException.getMessage());
		} else if (exception instanceof RemoteServiceException) {
			RemoteServiceException remoteServiceException = (RemoteServiceException) exception;
			response.setErrorCode(remoteServiceException.getCode());
			response.setErrorMsg(remoteServiceException.getMessage());
		} else {
			response.setErrorCode(ExceptionCodeEnum.EXCEPTION.name());
			response.setErrorMsg(ExceptionCodeEnum.EXCEPTION.getMessage());
		}
		return response;
	}

	/**
	 * 常规阻断校验异常类集合(这些报错不能算是接口请求失败)
	 */
	private static final Class[] NORMAL_BLOCKING_EXCEPTION_CLASS_ARRAY =
			{ServiceException.class, IllegalArgumentException.class};

	/**
	 * 判断是否为常规阻断校验异常
	 */
	public static boolean isNormalBlockingException(Throwable throwable) {
		for (Class customizedExceptionClass : NORMAL_BLOCKING_EXCEPTION_CLASS_ARRAY) {
			if (customizedExceptionClass.isInstance(throwable)) {
				return true;
			}
		}
		return false;
	}

}
