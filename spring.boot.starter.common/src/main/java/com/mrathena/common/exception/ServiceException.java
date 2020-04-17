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
		} else if (cause instanceof Exception) {
			this.code = ExceptionEnum.EXCEPTION.name();
			this.info = ExceptionEnum.EXCEPTION.getInfo();
		} else {
			this.code = ExceptionEnum.ERROR.name();
			this.info = ExceptionEnum.ERROR.getInfo();
		}
		this.description = ExceptionHandler.getStackTraceStr(this);
	}

	public ServiceException(ExceptionEnumInterface exception, String description) {
		super(description);
		this.code = exception.name();
		this.info = exception.getInfo();
		this.description = description;
	}

	public ServiceException(String description) {
		this(ExceptionEnum.EXCEPTION, description);
	}

}
