package com.mrathena.spring.boot.starter.api.y2019.demo;

import com.mrathena.common.entity.Response;

/**
 * @author mrathena on 2019/10/17 21:39
 */
public interface DemoService {

	/**
	 * demoMethod
	 *
	 * @param request .
	 * @return .
	 */
	Response<DemoResDto> demoMethod(DemoReqDto request);

	/**
	 * demoMethod2
	 *
	 * @param request .
	 * @return .
	 */
	Response<String> demoMethod2(String request);

}
