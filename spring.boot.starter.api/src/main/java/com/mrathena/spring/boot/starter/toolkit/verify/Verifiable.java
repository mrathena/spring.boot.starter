package com.mrathena.spring.boot.starter.toolkit.verify;

/**
 * @author mrathena on 2019/11/14 13:46
 */
public interface Verifiable {

	/**
	 * 参数验证方法
	 */
	default void verify() {
		VerifyKit.verify(this);
	}

}
