package com.mrathena.service.business.y2020.customer;

import com.mrathena.biz.business.y2020.customer.CustomerBiz;
import com.mrathena.common.entity.Response;
import com.mrathena.spring.boot.starter.api.business.y2020.customer.CustomerService;
import com.mrathena.spring.boot.starter.api.business.y2020.customer.dto.CreateCustomerReqDTO;
import com.mrathena.spring.boot.starter.api.business.y2020.customer.dto.CustomerDTO;
import com.mrathena.spring.boot.starter.api.business.y2020.customer.dto.QueryCustomerReqDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

	@Resource
	private CustomerBiz customerBiz;

	@Override
	public Response<Boolean> create(CreateCustomerReqDTO request) {
		return new Response<>(customerBiz.create(request));
	}

	@Override
	public Response<CustomerDTO> queryCustomer(QueryCustomerReqDTO request) {
		return new Response<>(customerBiz.queryCustomer(request));
	}

	@Override
	public Response<List<CustomerDTO>> queryAllCustomer() {
		return new Response<>(customerBiz.queryAllCustomer());
	}

}
