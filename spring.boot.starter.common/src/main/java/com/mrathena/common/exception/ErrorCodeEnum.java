package com.mrathena.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author com.mrathena on 2019/5/27 10:19
 */
@Getter
@AllArgsConstructor
public enum ErrorCodeEnum implements ErrorCodeEnumInterface {

	/**
	 * 常规错误码
	 */
	ERROR("系统错误"),
	EXCEPTION("系统异常"),
	ILLEGAL_ARGUMENT("非法参数"),
	LOCK_CONFLICT("请求太频繁了，请稍后重试"),
	LOCK_EXCEPTION("锁异常"),
	DATA_EXCEPTION("数据异常"),
	REMOTE_SERVICE_UNAVAILABLE("远程服务异常"),
	REMOTE_SERVICE_INVOKE_TIMEOUT("远程服务异常"),
	REMOTE_SERVICE_INVOKE_FAILURE("远程服务异常");

	/**
	 * 给用户看的内容
	 */
	private final String info;

}
