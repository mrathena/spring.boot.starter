package com.mrathena.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author com.mrathena on 2019/5/27 10:19
 */
@Getter
@AllArgsConstructor
public enum BusinessErrorCodeEnum implements BusinessErrorCodeEnumInterface {

	/**
	 * 常规错误码
	 */
	ILLEGAL_ARGUMENT("非法参数"),
	LOCK_CONFLICT("锁冲突"),
	;

	/**
	 * 给用户看的内容
	 */
	private final String info;

}
