package com.mrathena.web.controller;

import com.mrathena.common.toolkit.IdKit;
import com.mrathena.spring.boot.starter.api.business.y2020.customer.CustomerService;
import com.mrathena.spring.boot.starter.api.business.y2020.customer.dto.CreateCustomerReqDTO;
import com.mrathena.spring.boot.starter.api.business.y2020.customer.dto.QueryCustomerReqDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author mrathena on 2019/5/8 16:48
 */
@Slf4j
@RestController
@RequestMapping("customer")
public class CustomerController {

	@Resource
	private CustomerService customerService;

	/**
	 * 测试 service
	 */
	@RequestMapping("create")
	public Object create(String mobile, String email) {
		CreateCustomerReqDTO request = new CreateCustomerReqDTO();
		request.setNickname("mrathena-" + IdKit.getUuid().substring(16));
		request.setUsername("username-" + IdKit.getUuid().substring(0, 10));
		request.setPassword(IdKit.getUuid());
		request.setMobile(mobile);
		request.setEmail(email);
		return customerService.create(request);
	}

	/**
	 * 测试 service
	 */
	@GetMapping("query/{customerNo}")
	public Object query(@PathVariable String customerNo) {
		QueryCustomerReqDTO request = new QueryCustomerReqDTO();
		request.setTraceNo(IdKit.getSerialNo());
		request.setCustomerNo(customerNo);
		return customerService.queryCustomer(request);
	}
	/**
	 * 测试 service
	 */
	@GetMapping("query/all")
	public Object query() {
		return customerService.queryAllCustomer();
	}

}
