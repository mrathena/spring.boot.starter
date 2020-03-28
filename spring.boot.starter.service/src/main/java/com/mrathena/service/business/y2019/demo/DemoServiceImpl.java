package com.mrathena.service.business.y2019.demo;

import com.mrathena.biz.business.y2019.demo.DemoBiz;
import com.mrathena.common.entity.Response;
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
		return new Response.Builder<>(demoBiz.createDemo(request)).build();
	}

	@Override
	public Response<QueryDemoResDTO> queryDemo(QueryDemoReqDTO request) {
		return new Response.Builder<>(demoBiz.queryDemo(request)).build();
	}

	@Override
	public Response<BasePageResDTO<DemoDTO>> queryDemoWithPage(QueryDemoReqDTO request) {
		try {
			return new Response.Builder<>(demoBiz.queryDemoWithPage(request)).build();
		} catch (IllegalAccessException e) {
			// 这种不得不处理的异常, 用RuntimeException, 因为ServiceException默认是当作正常的阻断
			throw new RuntimeException(e);
		}
	}
}
