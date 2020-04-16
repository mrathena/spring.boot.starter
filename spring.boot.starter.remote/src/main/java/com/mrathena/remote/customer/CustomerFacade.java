package com.mrathena.remote.customer;

import com.mrathena.common.entity.Response;
import com.mrathena.common.exception.RemoteServiceException;
import com.mrathena.common.exception.ServiceException;
import com.mrathena.remote.demo.CustomerDto;
import com.mrathena.remote.demo.CustomerQueryReqDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author mrathena on 2020-03-29 05:52
 */
@Slf4j
@Component
public class CustomerFacade {

	@Resource
	private CustomerIntegration integration;

	public CustomerDto queryCustomer(String mobile) {
		try {
			CustomerQueryReqDto request = new CustomerQueryReqDto();
			request.setMobile(mobile);
			Response<CustomerDto> response = integration.queryCustomer(request);
			return response.getResult();
		} catch (ServiceException e) {
			throw e;
		} catch (Throwable cause) {
			throw new RemoteServiceException(cause);
		}
	}

}
