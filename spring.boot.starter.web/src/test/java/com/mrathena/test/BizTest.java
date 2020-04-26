package com.mrathena.test;

import com.mrathena.biz.business.y2020.customer.CustomerBiz;
import com.mrathena.common.toolkit.IdKit;
import com.mrathena.spring.boot.starter.api.business.y2020.customer.dto.CreateCustomerReqDTO;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author mrathena on 2019/12/17 17:17
 */
public class BizTest extends BaseTest {

	@Resource
	private CustomerBiz customerBiz;

	@Test
	public void test() {
		CreateCustomerReqDTO request = new CreateCustomerReqDTO();
		request.setNickname("mrathena-" + IdKit.getUuid().substring(16));
		request.setUsername("username-" + IdKit.getUuid().substring(0, 10));
		request.setPassword(IdKit.getUuid());
		request.setMobile("18234089811");
		request.setEmail("747779761@qq.com");
		customerBiz.create(request);
	}

}
