package com.mrathena.biz.business.y2020.customer;

import com.mrathena.common.exception.BusinessErrorCodeEnum;
import com.mrathena.common.exception.BusinessException;
import com.mrathena.common.exception.ServiceErrorCodeEnum;
import com.mrathena.common.exception.ServiceException;
import com.mrathena.dao.manager.y2020.customer.CustomerManager;
import com.mrathena.spring.boot.starter.api.business.y2020.customer.dto.CreateCustomerReqDTO;
import com.mrathena.spring.boot.starter.api.business.y2020.customer.dto.CustomerDTO;
import com.mrathena.spring.boot.starter.api.business.y2020.customer.dto.QueryCustomerReqDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Component
public class CustomerBiz {

	@Resource
	private CustomerManager customerManager;

	public boolean create(CreateCustomerReqDTO request) {
		if (request.getMobile().equals("18234089810")) {
			throw new BusinessException(BusinessErrorCodeEnum.LOCK_CONFLICT, "demo business exception");
		}
		if (request.getMobile().equals("18234089811")) {
			throw new ServiceException(ServiceErrorCodeEnum.ERROR, "demo service exception");
		}
		if (request.getMobile().equals("18234089812")) {
			System.out.println(1 / 0);
		}
		if (request.getMobile().equals("18234089813")) {
			try {
				System.out.println(2 / 0);
			} catch (Throwable cause) {
				throw new ServiceException(cause);
			}
		}
		return false;
	}

	public CustomerDTO queryCustomer(QueryCustomerReqDTO request) {

		return null;
	}

	public List<CustomerDTO> queryAllCustomer() {
		return null;
	}

}
