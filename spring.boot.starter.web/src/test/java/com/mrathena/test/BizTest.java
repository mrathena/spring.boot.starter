package com.mrathena.test;

import com.mrathena.biz.business.y2019.demo.DemoBiz;
import com.mrathena.common.toolkit.IdKit;
import com.mrathena.spring.boot.starter.api.business.y2019.demo.QueryDemoReqDTO;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author mrathena on 2019/12/17 17:17
 */
public class BizTest extends BaseTest {

	@Resource
	private DemoBiz biz;

	@Test
	public void test() {
		QueryDemoReqDTO request = new QueryDemoReqDTO();
		request.setTraceNo(IdKit.getSerialNo());
		request.setId(37L);
		biz.queryDemo(request);
		biz.queryDemo(request);
		biz.queryDemo(request);
		biz.queryDemo(request);
		biz.queryDemo(request);
	}

}
