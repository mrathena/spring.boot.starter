package com.mrathena.test;

import com.mrathena.common.toolkit.IdKit;
import com.mrathena.spring.boot.starter.api.business.y2020.customer.CustomerService;
import com.mrathena.spring.boot.starter.api.business.y2020.customer.dto.CreateCustomerReqDTO;
import org.junit.Test;

import javax.annotation.Resource;

public class ServiceTest extends BaseTest {

	@Resource
	private CustomerService customerService;

	@Test
	public void test() {
		CreateCustomerReqDTO request = new CreateCustomerReqDTO();
		request.setNickname("mrathena-" + IdKit.getUuid().substring(16));
		request.setUsername("username-" + IdKit.getUuid().substring(0, 10));
		request.setPassword(IdKit.getUuid());
		request.setMobile("18234089811");
		request.setEmail("747779761@qq.com");
		customerService.create(request);

		request.setEmail("747779762@qq.com");
		customerService.create(request);

		request.setMobile("18234089812");
		customerService.create(request);
	}

}
