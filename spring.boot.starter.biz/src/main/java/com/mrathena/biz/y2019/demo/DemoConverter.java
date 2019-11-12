package com.mrathena.biz.y2019.demo;

import com.mrathena.biz.toolkit.BaseConverter;
import com.mrathena.dao.entity.y2019.demo.DemoDO;
import com.mrathena.spring.boot.starter.api.y2019.demo.DemoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author mrathena on 2019/11/12 18:22
 */
@Mapper(componentModel = "spring")
public interface DemoConverter extends BaseConverter<DemoDO, DemoDto> {

	/**
	 * to
	 *
	 * @param source do
	 * @return dto
	 */
	@Mapping(source = "demo", target = "anotherDemo")
	@Override
	DemoDto to(DemoDO source);

	/**
	 * from
	 *
	 * @param source dto
	 * @return do
	 */
	@Mapping(source = "anotherDemo", target = "demo")
	@Override
	DemoDO from(DemoDto source);

}
