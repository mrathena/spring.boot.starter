package com.mrathena.spring.boot.starter.api.business.y2020.customer;

import com.mrathena.common.entity.Response;
import com.mrathena.spring.boot.starter.api.business.y2020.customer.dto.CreateCustomerReqDTO;
import com.mrathena.spring.boot.starter.api.business.y2020.customer.dto.CustomerDTO;
import com.mrathena.spring.boot.starter.api.business.y2020.customer.dto.QueryCustomerReqDTO;

import java.util.List;

public interface CustomerService {

	Response<Boolean> create(CreateCustomerReqDTO request);

	Response<CustomerDTO> queryCustomer(QueryCustomerReqDTO request);

	Response<List<CustomerDTO>> queryAllCustomer();

}
