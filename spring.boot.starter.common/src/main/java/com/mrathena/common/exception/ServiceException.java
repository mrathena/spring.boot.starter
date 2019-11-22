package com.mrathena.common.exception;

import lombok.Getter;

/**
 * code:异常码
 * message:给用户看的信息
 * description: RootCauseDescription
 * <p>
 *
 * @author mrathena on 2019/5/27 10:10
 */
@Getter
public class ServiceException extends RuntimeException {

	private String code;
	private String description;

	/**
	 * 用于主动捕获
	 */
	public ServiceException(Throwable throwable) {
		super(throwable.getMessage(), throwable);
		if (throwable instanceof ServiceException) {
			ServiceException exception = (ServiceException) throwable;
			this.code = exception.getCode();
			this.description = exception.getDescription();
		} else if (throwable instanceof RemoteServiceException) {
			RemoteServiceException exception = (RemoteServiceException) throwable;
			this.code = exception.getCode();
			this.description = exception.getDescription();
		} else {
			this.code = ExceptionCodeEnum.EXCEPTION.name();
			this.description = ExceptionHandler.getStackTraceStr(throwable);
		}
	}

	/**
	 * 用于主动抛出
	 */
	public ServiceException(String code, String message, String description) {
		super(message);
		this.code = code;
		this.description = description;
	}

	/**
	 * 用于主动抛出
	 */
	public ServiceException(ExceptionCodeInterface code) {
		this(code.getCode(), code.getMessage(), code.getDescription());
	}

	/**
	 * 用于主动抛出
	 */
	public ServiceException(String message, String description) {
		this(ExceptionCodeEnum.EXCEPTION.name(), message, description);
	}

}
