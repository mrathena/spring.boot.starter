package com.mrathena.remote.customer;

import com.mrathena.common.entity.Response;
import com.mrathena.common.toolkit.LogKit;
import com.mrathena.remote.demo.CustomerDto;
import com.mrathena.remote.demo.CustomerQueryReqDto;
import com.mrathena.remote.demo.CustomerService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

/**
 * @author mrathena on 2020-03-29 05:51
 */
@Component
public class CustomerIntegration {

	@Reference(timeout = 1000, check = false)
	private CustomerService customerService;

	public Response<CustomerDto> queryCustomer(CustomerQueryReqDto request) {
		return customerService.queryCustomer(request, LogKit.getTraceNo());
	}

}
