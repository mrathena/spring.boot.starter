package com.mrathena.test;

import com.bestpay.basic.service.request.BasicRequest;
import com.mratehna.remote.y2019.demo.ProvinceServiceIntegration;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author mrathena on 2019/10/22 1:24
 */
@Slf4j
public class DubboConsumerTest extends BaseTest {

	@Autowired(required = false)
	private ProvinceServiceIntegration provinceServiceIntegration;

	@Test
	public void test() {
		System.out.println(provinceServiceIntegration.queryAllProvince(new BasicRequest()));
	}

}
