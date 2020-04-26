package com.mrathena.common.exception;

import lombok.Getter;

/**
 * 常规阻断式异常, 该异常被认为是正常
 *
 * @author com.mrathena on 2020-03-29 03:55
 */
@Getter
public class BusinessException extends RuntimeException {

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

	private BusinessException(String code, String info, String description) {
		super(description);
		this.code = code;
		this.info = info;
		this.description = description;
	}

	public BusinessException(BusinessErrorCodeEnumInterface exception, String info, String description) {
		this(exception.name(), info, description);
	}

	public BusinessException(BusinessErrorCodeEnumInterface exception, String description) {
		this(exception.name(), exception.getInfo(), description);
	}

}
