package com.mrathena.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author com.mrathena on 2019/5/27 10:19
 */
@Getter
@AllArgsConstructor
public enum ServiceErrorCodeEnum implements ServiceErrorCodeEnumInterface {

	/**
	 * 常规错误码
	 */
	ERROR("系统错误"),
	EXCEPTION("系统异常"),
	REMOTE_SERVICE_UNAVAILABLE("远程服务异常"),
	REMOTE_SERVICE_INVOKE_TIMEOUT("远程服务异常"),
	REMOTE_SERVICE_INVOKE_FAILURE("远程服务异常");

	/**
	 * 给用户看的内容
	 */
	private final String info;

}
