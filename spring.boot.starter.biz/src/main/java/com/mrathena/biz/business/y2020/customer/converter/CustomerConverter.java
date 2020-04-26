package com.mrathena.biz.business.y2020.customer.converter;

import com.mrathena.biz.toolkit.BaseConverter;
import com.mrathena.dao.entity.y2020.customer.CustomerDO;
import com.mrathena.spring.boot.starter.api.business.y2020.customer.dto.CustomerDTO;
import org.mapstruct.Mapper;

/**
 * @author mrathena on 2019/11/12 18:22
 */
@Mapper(componentModel = "spring")
public interface CustomerConverter extends BaseConverter<CustomerDO, CustomerDTO> {}