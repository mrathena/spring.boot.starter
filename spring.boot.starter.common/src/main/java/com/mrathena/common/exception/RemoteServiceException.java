package com.mrathena.common.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 远程调用异常类
 * <p>
 * 约定:所有RPC服务调用的封装,如果主动抛出异常/捕获异常后再抛出,只能报该类异常
 *
 * @author mrathena on 2019/7/18 15:59
 */
@Slf4j
@Getter
public final class RemoteServiceException extends RuntimeException {

	private String code;
	private String description;

	/**
	 * 用于主动捕获
	 */
	public RemoteServiceException(Throwable throwable) {
		super(throwable.getMessage(), throwable);
		if (throwable instanceof RemoteServiceException) {
			RemoteServiceException exception = (RemoteServiceException) throwable;
			this.code = exception.getCode();
			this.description = exception.getDescription();
		} else {
			if (ExceptionHandler.isDubboUnavailableException(throwable)) {
				this.code = ExceptionCodeEnum.REMOTE_SERVICE_UNAVAILABLE.name();
			} else if (ExceptionHandler.isDubboTimeoutException(throwable)) {
				this.code = ExceptionCodeEnum.REMOTE_SERVICE_INVOKE_TIMEOUT.name();
			} else {
				this.code = ExceptionCodeEnum.REMOTE_SERVICE_INVOKE_FAILURE.name();
			}
			this.description = ExceptionHandler.getStackTraceStr(throwable);
		}

	}

	/**
	 * 用于主动抛出
	 * Unavailable和Timeout两种异常都是通过传入Throwable判断出来的,主动抛出的都是Failure
	 */
	public RemoteServiceException(String message, String description) {
		super(message);
		this.code = ExceptionCodeEnum.REMOTE_SERVICE_INVOKE_FAILURE.name();
		this.description = description;
	}

	/**
	 * 判断RemoteServiceException是否是不可用异常
	 * ExceptionHandler#isDubboUnavailableException(java.lang.Exception)
	 */
	public boolean isUnavailable() {
		return ExceptionCodeEnum.REMOTE_SERVICE_UNAVAILABLE.name().equals(code);
	}

	/**
	 * 判断RemoteServiceException是否是超时异常
	 * ExceptionHandler#isDubboTimeoutException(java.lang.Exception)
	 */
	public boolean isTimeout() {
		return ExceptionCodeEnum.REMOTE_SERVICE_INVOKE_TIMEOUT.name().equals(code);
	}

	/**
	 * 判断RemoteServiceException是否是失败异常
	 */
	public boolean isFailure() {
		return ExceptionCodeEnum.REMOTE_SERVICE_INVOKE_FAILURE.name().equals(code);
	}
}
