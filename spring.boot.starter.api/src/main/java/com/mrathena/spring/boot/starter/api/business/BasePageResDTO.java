package com.mrathena.spring.boot.starter.api.business;

import lombok.*;
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
@NoArgsConstructor
@AllArgsConstructor
public class BasePageResDTO<T> implements Serializable {

	/**
	 * 总数
	 */
	private Long total;

	/**
	 * 当前页数据
	 */
	private List<T> list;

	public static <T> BasePageResDTO<T> empty(Class<T> clazz) {
		return new BasePageResDTO<T>().setTotal(0L).setList(new LinkedList<>());
	}

}
