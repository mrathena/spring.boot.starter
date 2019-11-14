package com.mrathena.spring.boot.starter.api.y2019.demo;

import com.bestpay.dubbo.result.Result;
import com.mrathena.spring.boot.starter.api.BasePageResDTO;

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
	Result<Boolean> createDemo(CreateDemoReqDTO request);

	/**
	 * queryDemo
	 *
	 * @param request .
	 * @return .
	 */
	Result<QueryDemoResDTO> queryDemo(QueryDemoReqDTO request);

	/**
	 * queryDemoWithPage
	 *
	 * @param request .
	 * @return .
	 */
	Result<BasePageResDTO<DemoDTO>> queryDemoWithPage(QueryDemoReqDTO request);

}
