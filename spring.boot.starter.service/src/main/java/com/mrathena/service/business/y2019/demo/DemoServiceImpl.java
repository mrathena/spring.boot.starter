package com.mrathena.service.business.y2019.demo;

import com.mrathena.biz.business.y2019.demo.DemoBiz;
import com.mrathena.common.entity.Response;
import com.mrathena.common.exception.ServiceException;
import com.mrathena.spring.boot.starter.api.BasePageResDTO;
import com.mrathena.spring.boot.starter.api.business.y2019.demo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author mrathena on 2019/11/7 18:00
 */
@Slf4j
@Service
@Component
public class DemoServiceImpl implements DemoService {

	@Resource
	private DemoBiz demoBiz;

	@Override
	public Response<Boolean> createDemo(CreateDemoReqDTO request) {
		return new Response<>(demoBiz.createDemo(request));
	}

	@Override
	public Response<QueryDemoResDTO> queryDemo(QueryDemoReqDTO request) {
		return new Response<>(demoBiz.queryDemo(request));
	}

	@Override
	public Response<BasePageResDTO<DemoDTO>> queryDemoWithPage(QueryDemoReqDTO request) {
		try {
			return new Response<>(demoBiz.queryDemoWithPage(request));
		} catch (IllegalAccessException e) {
			throw new ServiceException(e);
		}
	}
}
