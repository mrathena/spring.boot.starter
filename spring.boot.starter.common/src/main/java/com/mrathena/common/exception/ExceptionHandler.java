package com.mrathena.common.exception;

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

	public static void main(String[] args) {
		try {
			throw new ServiceException(ExceptionCodeEnum.EXCEPTION);
		} catch (Exception e) {
			System.out.println(ExceptionHandler.getClassAndMessageWithoutCustomizedException(e));
			System.out.println(ExceptionHandler.getDescriptionWithoutCustomizedException(e, "你猜"));
		}
		System.out.println();
		try {
			int a = 0;
			System.out.println(10 / a);
		} catch (Exception e) {
			System.out.println(ExceptionHandler.getClassAndMessageWithoutCustomizedException(e));
			System.out.println(ExceptionHandler.getDescriptionWithoutCustomizedException(e, "你猜"));
		}
	}

	private ExceptionHandler() {}

	/**
	 * 自定义的异常类集合
	 */
	private static final Class[] CUSTOMIZED_EXCEPTION_CLASS_ARRAY =
			{ServiceException.class, RemoteServiceException.class};

	/**
	 * 获取异常的堆栈信息
	 */
	public static String getStackTrace(Throwable throwable) {
		return ExceptionUtils.getStackTrace(throwable);
	}

	/**
	 * 获取根源异常的堆栈信息
	 */
	public static String getRootCauseStackTrace(Throwable throwable) {
		return ExceptionUtils.getStackTrace(ExceptionUtils.getRootCause(throwable));
	}

	/**
	 * 获取Exception的类名和信息
	 * java.lang.NullPointerException: null
	 * com.mrathena.common.exception.ExceptionCodeEnum: 客户不存在
	 */
	public static String getClassAndMessage(Exception exception) {
		return exception.getClass().getName() + Constant.COLON + Constant.BLANK + exception.getMessage();
	}

	/**
	 * 获取Exception的类名和信息(排除自定义的异常类的类名)
	 * java.lang.NullPointerException: null
	 * 客户不存在
	 */
	public static String getClassAndMessageWithoutCustomizedException(Exception exception) {
		String result = Constant.EMPTY;
		boolean isCustomizedException = false;
		for (Class customizedExceptionClass : CUSTOMIZED_EXCEPTION_CLASS_ARRAY) {
			if (customizedExceptionClass.isInstance(exception)) {
				isCustomizedException = true;
				break;
			}
		}
		if (!isCustomizedException) {
			result += exception.getClass().getName() + Constant.COLON + Constant.BLANK;
		}
		result += exception.getMessage();
		return result;
	}

	/**
	 * 获取异常描述信息
	 * 获取客户信息异常[java.lang.NullPointerException: null]
	 * 获取客户信息异常[com.mrathena.common.exception.ExceptionCodeEnum: 客户不存在]
	 */
	public static String getDescription(Exception exception, String content) {
		return content + Constant.L_BRACKET + getClassAndMessage(exception) + Constant.R_BRACKET;
	}

	/**
	 * 获取异常描述信息(排除自定义的异常类的类名)
	 * 获取客户信息异常[java.lang.NullPointerException: null]
	 * 获取客户信息异常[客户不存在]
	 */
	public static String getDescriptionWithoutCustomizedException(Exception exception, String content) {
		return content + Constant.L_BRACKET + getClassAndMessageWithoutCustomizedException(exception) + Constant.R_BRACKET;
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

}
