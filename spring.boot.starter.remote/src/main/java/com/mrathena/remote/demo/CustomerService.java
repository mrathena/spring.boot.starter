package com.mrathena.remote.demo;

import com.mrathena.common.entity.Response;

/**
 * @author mrathena on 2020-03-29 05:54
 */
public interface CustomerService {

	Response<CustomerDto> queryCustomer(CustomerQueryReqDto request, String traceNo);

}
