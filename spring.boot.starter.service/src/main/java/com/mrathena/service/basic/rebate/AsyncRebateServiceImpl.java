package com.mrathena.service.basic.rebate;

import com.mrathena.biz.basic.rebate.AsyncRebateBiz;
import com.mrathena.common.entity.Response;
import com.mrathena.spring.boot.starter.api.basic.rebate.AsyncRebateService;
import com.mrathena.spring.boot.starter.api.basic.rebate.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author mrathena on 2019/11/22 11:37
 */
@Slf4j
@Service
@Component
public class AsyncRebateServiceImpl implements AsyncRebateService {

	@Resource
	private AsyncRebateBiz asyncRebateBiz;

	@Override
	public Response<AsyncRebateAcceptResDTO> rebate(AsyncRebateAcceptReqDTO request) {
		return new Response.Builder<>(asyncRebateBiz.rebate(request)).build();
	}

	@Override
	public Response<AsyncRebateCancelAcceptResDTO> cancel(AsyncRebateCancelAcceptReqDTO request) {
		return new Response.Builder<>(asyncRebateBiz.cancel(request)).build();
	}

	@Override
	public Response<AsyncRebateTradeQueryResDTO> query(AsyncRebateTradeQueryReqDTO request) {
		return new Response.Builder<>(asyncRebateBiz.query(request)).build();
	}
}
