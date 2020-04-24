package com.mrathena.common.entity;

import com.mrathena.common.exception.ExceptionEnumInterface;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author com.mrathena
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class Response<T> implements Serializable {

	/**
	 * 是否调用成功(是否远程服务执行成功/是否远程服务没有报错)
	 */
	private boolean success;
	private T result;
	private String code;
	private String message;

	public Response(T result) {
		this.success = true;
		this.result = result;
	}

	public Response(String code, String message) {
		this.success = false;
		this.code = code;
		this.message = message;
	}

	public Response(ExceptionEnumInterface exception) {
		this.success = false;
		this.code = exception.name();
		this.message = exception.getInfo();
	}

}
