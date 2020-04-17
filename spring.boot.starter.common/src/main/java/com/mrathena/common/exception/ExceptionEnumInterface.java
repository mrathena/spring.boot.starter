package com.mrathena.common.exception;

/**
 * @author com.mrathena on 2019/10/17 15:27
 */
public interface ExceptionEnumInterface {

	/**
	 * 异常码
	 *
	 * @return .
	 */
	String name();

	/**
	 * 给用户看的内容
	 *
	 * @return .
	 */
	String getInfo();

}
