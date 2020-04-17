package com.mrathena.biz.toolkit;


import org.mapstruct.Mappings;

import java.util.Collection;
import java.util.List;

/**
 * 基础转换类(一对一), 写一个接口直接继承这个接口就好了
 * 提供基本的4个方法, 如果有需要, 在子接口的 {@link #to(Object)} 方法上加 @Mappings / @Mapping 注解, 只会新增而不会影响原有映射
 * 子接口要加上 @Mapper(componentModel = "spring")
 *
 * @author mrathena on 2019/11/12 17:45
 */
public interface BaseConverter<DO, DTO> {

	/**
	 * 如有需要, 自定义 @Mappings
	 *
	 * @param source do
	 * @return dto
	 */
	@Mappings({})
	DTO to(DO source);

	/**
	 * to
	 *
	 * @param source doList
	 * @return dtoList
	 */
	List<DTO> to(Collection<DO> source);

	/**
	 * from
	 *
	 * @param source dto
	 * @return do
	 */
	@Mappings({})
	DO from(DTO source);

	/**
	 * from
	 *
	 * @param source dtoList
	 * @return doList
	 */
	List<DO> from(Collection<DTO> source);

}
