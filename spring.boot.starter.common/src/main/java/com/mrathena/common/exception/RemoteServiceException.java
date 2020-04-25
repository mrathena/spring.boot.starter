package com.mrathena.common.exception;

import lombok.Getter;

/**
 * @author com.mrathena on 2019/7/18 15:59
 */
@Getter
public final class RemoteServiceException extends ServiceException {

	public RemoteServiceException(Throwable cause) {
		super(cause);
		if (cause instanceof ServiceException) {
			ServiceException exception = (ServiceException) cause;
			this.code = exception.getCode();
			this.info = exception.getInfo();
			this.description = exception.getDescription();
		} else {
			if (ExceptionHandler.isDubboUnavailableException(cause)) {
				this.code = ErrorCodeEnum.REMOTE_SERVICE_UNAVAILABLE.name();
				this.info = ErrorCodeEnum.REMOTE_SERVICE_UNAVAILABLE.getInfo();
			} else if (ExceptionHandler.isDubboTimeoutException(cause)) {
				this.code = ErrorCodeEnum.REMOTE_SERVICE_INVOKE_TIMEOUT.name();
				this.info = ErrorCodeEnum.REMOTE_SERVICE_INVOKE_TIMEOUT.getInfo();
			} else {
				this.code = ErrorCodeEnum.REMOTE_SERVICE_INVOKE_FAILURE.name();
				this.info = ErrorCodeEnum.REMOTE_SERVICE_INVOKE_FAILURE.getInfo();
			}
			this.description = ExceptionHandler.getStackTraceStr(this);
		}
	}

	public RemoteServiceException(ErrorCodeEnumInterface exception, String description) {
		super(description);
		this.code = exception.name();
		this.info = exception.getInfo();
		this.description = description;
	}

	public RemoteServiceException(String description) {
		this(ErrorCodeEnum.EXCEPTION, description);
	}

}
