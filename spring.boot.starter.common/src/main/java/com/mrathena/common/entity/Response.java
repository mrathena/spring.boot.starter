package com.mrathena.common.entity;

import com.mrathena.common.exception.ExceptionEnumInterface;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author com.mrathena
 */
@Getter
@ToString
@Accessors(chain = true)
public class Response<T> implements Serializable {

	/**
	 * 是否调用成功(是否远程服务执行成功/是否远程服务没有报错)
	 */
	private final boolean success;
	private final T result;
	private final String code;
	private final String message;

	private Response(Builder<T> builder) {
		this.success = builder.success;
		this.result = builder.result;
		this.code = builder.code;
		this.message = builder.message;
	}

	public static class Builder<T> {
		/**
		 * required
		 */
		private final boolean success;
		/**
		 * optional
		 */
		private T result;
		private String code;
		private String message;

		public Builder(T result) {
			this.success = true;
			this.result = result;
		}

		public Builder(String code, String message) {
			this.success = false;
			this.code = code;
			this.message = message;
		}

		public Builder(ExceptionEnumInterface exception) {
			this.success = false;
			this.code = exception.name();
			this.message = exception.getInfo();
		}

		public Response<T> build() {
			return new Response<>(this);
		}
	}

}
