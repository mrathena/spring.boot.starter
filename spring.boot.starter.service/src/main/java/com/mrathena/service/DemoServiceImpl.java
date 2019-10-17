package com.mrathena.service;

import com.mrathena.common.entity.Response;
import com.mrathena.spring.boot.starter.api.DemoReqDto;
import com.mrathena.spring.boot.starter.api.DemoResDto;
import com.mrathena.spring.boot.starter.api.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author mrathena on 2019/10/17 21:58
 */
@Slf4j
@Service
public class DemoServiceImpl implements DemoService {

	/**
	 * demoMethod
	 */
	@Override
	public Response<DemoResDto> demoMethod(DemoReqDto request) {
		return null;
	}

	/**
	 * demoMethod2
	 */
	@Override
	public Response<String> demoMethod2(String request) {
		return null;
	}
}
