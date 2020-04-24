package com.mrathena.test;

import com.mrathena.spring.boot.starter.api.business.y2019.demo.DemoService;
import org.junit.Test;

import javax.annotation.Resource;

public class ServiceTest extends BaseTest {

	@Resource
	private DemoService demoService;

	@Test
	public void test() {
		demoService.demo();
		demoService.demo();
		demoService.demo();
		demoService.demo();
		demoService.demo();
	}

}
