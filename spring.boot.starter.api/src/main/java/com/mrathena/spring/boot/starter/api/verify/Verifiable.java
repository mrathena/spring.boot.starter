package com.mrathena.spring.boot.starter.api.verify;

/**
 * @author com.mrathena on 2020-03-29 02:38
 */
public interface Verifiable {

	/**
	 * 参数验证方法
	 */
	default void verify() {
		VerifyKit.validate(this);
	}

}
