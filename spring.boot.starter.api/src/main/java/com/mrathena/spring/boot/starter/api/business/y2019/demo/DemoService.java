package com.mrathena.spring.boot.starter.api.business.y2019.demo;

import com.mrathena.common.entity.Response;
import com.mrathena.spring.boot.starter.api.business.BasePageResDTO;

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
	Response<Boolean> createDemo(CreateDemoReqDTO request);

	/**
	 * queryDemo
	 *
	 * @param request .
	 * @return .
	 */
	Response<QueryDemoResDTO> queryDemo(QueryDemoReqDTO request);

	/**
	 * queryDemoWithPage
	 *
	 * @param request .
	 * @return .
	 */
	Response<BasePageResDTO<DemoDTO>> queryDemoWithPage(QueryDemoReqDTO request);

}
