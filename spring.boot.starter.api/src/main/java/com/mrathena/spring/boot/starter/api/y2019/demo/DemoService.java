package com.mrathena.spring.boot.starter.api.y2019.demo;

import com.bestpay.dubbo.result.Result;
import com.mrathena.spring.boot.starter.api.BasePageResDto;

/**
 * @author mrathena on 2019/10/17 21:39
 */
public interface DemoService {

	/**
	 * createDemo
	 *
	 * @param request .
	 * @return .
	 */
	Result<Boolean> createDemo(CreateDemoReqDto request);

	/**
	 * queryDemo
	 *
	 * @param request .
	 * @return .
	 */
	Result<QueryDemoResDto> queryDemo(QueryDemoReqDto request);

	/**
	 * queryDemoWithPage
	 *
	 * @param request .
	 * @return .
	 */
	Result<BasePageResDto<DemoDto>> queryDemoWithPage(QueryDemoReqDto request);

}
