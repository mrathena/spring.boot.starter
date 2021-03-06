package com.mrathena.common.exception;

import lombok.Getter;

/**
 * 本地方法抛出的非常规阻断异常, 不能被认为是正常情况的异常
 *
 * @author com.mrathena on 2019/5/27 10:10
 */
@Getter
public class ServiceException extends RuntimeException {

	/**
	 * 异常码
	 */
	protected String code;
	/**
	 * 给用户看的信息
	 */
	protected String info;
	/**
	 * 最原始的错误信息, 是给开发看的, 会打印在日志里
	 */
	protected String description;

	public ServiceException(Throwable cause) {
		super(cause.getMessage(), cause);
		if (cause instanceof ServiceException) {
			ServiceException exception = (ServiceException) cause;
			this.code = exception.getCode();
			this.info = exception.getInfo();
			this.description = exception.getDescription();
		} else if (cause instanceof Exception) {
			this.code = ServiceErrorCodeEnum.EXCEPTION.name();
			this.info = ServiceErrorCodeEnum.EXCEPTION.getInfo();
			this.description = ThrowableHandler.getStackTraceStr(this);
		} else {
			this.code = ServiceErrorCodeEnum.ERROR.name();
			this.info = ServiceErrorCodeEnum.ERROR.getInfo();
			this.description = ThrowableHandler.getStackTraceStr(this);
		}
	}

	public ServiceException(ServiceErrorCodeEnumInterface exception, String description) {
		super(description);
		this.code = exception.name();
		this.info = exception.getInfo();
		this.description = description;
	}

	public ServiceException(String description) {
		this(ServiceErrorCodeEnum.EXCEPTION, description);
	}

}
