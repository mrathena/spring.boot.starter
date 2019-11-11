package com.mrathena.spring.boot.starter.api;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author mrathena on 2019/11/7 16:00
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class BasePageResDto<T> implements Serializable {

	/**
	 * 总数
	 */
	private Long total;

	/**
	 * 当前页数据
	 */
	private List<T> list;

	public static <T> BasePageResDto<T> empty(Class<T> clazz) {
		return new BasePageResDto<T>().setTotal(0L).setList(new LinkedList<>());
	}

}
