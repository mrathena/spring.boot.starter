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
			if (ThrowableHandler.isDubboUnavailableException(cause)) {
				this.code = ServiceErrorCodeEnum.REMOTE_SERVICE_UNAVAILABLE.name();
				this.info = ServiceErrorCodeEnum.REMOTE_SERVICE_UNAVAILABLE.getInfo();
			} else if (ThrowableHandler.isDubboTimeoutException(cause)) {
				this.code = ServiceErrorCodeEnum.REMOTE_SERVICE_INVOKE_TIMEOUT.name();
				this.info = ServiceErrorCodeEnum.REMOTE_SERVICE_INVOKE_TIMEOUT.getInfo();
			} else {
				this.code = ServiceErrorCodeEnum.REMOTE_SERVICE_INVOKE_FAILURE.name();
				this.info = ServiceErrorCodeEnum.REMOTE_SERVICE_INVOKE_FAILURE.getInfo();
			}
			this.description = ThrowableHandler.getStackTraceStr(this);
		}
	}

	public RemoteServiceException(ServiceErrorCodeEnumInterface exception, String description) {
		super(description);
		this.code = exception.name();
		this.info = exception.getInfo();
		this.description = description;
	}

	public RemoteServiceException(String description) {
		this(ServiceErrorCodeEnum.REMOTE_SERVICE_INVOKE_FAILURE, description);
	}

}
