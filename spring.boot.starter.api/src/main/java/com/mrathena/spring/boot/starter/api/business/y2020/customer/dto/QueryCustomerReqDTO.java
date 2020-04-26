package com.mrathena.spring.boot.starter.api.business.y2020.customer.dto;

import com.mrathena.spring.boot.starter.api.business.BaseReqDTO;
import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
public class QueryCustomerReqDTO extends BaseReqDTO {

	private static final long serialVersionUID = 1L;

	private String customerNo;

}
