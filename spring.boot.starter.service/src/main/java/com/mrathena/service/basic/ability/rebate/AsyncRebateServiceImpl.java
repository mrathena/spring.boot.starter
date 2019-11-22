package com.mrathena.service.basic.ability.rebate;

import com.mrathena.common.entity.Response;
import com.mrathena.spring.boot.starter.api.basic.ability.rebate.AsyncRebateService;
import com.mrathena.spring.boot.starter.api.basic.ability.rebate.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author mrathena on 2019/11/22 11:37
 */
@Slf4j
@Component
public class AsyncRebateServiceImpl implements AsyncRebateService {

	@Override
	public Response<AsyncRebateAcceptResDTO> rebate(AsyncRebateAcceptReqDTO request) {
		return new Response<>(null);
	}

	@Override
	public Response<AsyncRebateCancelAcceptResDTO> cancel(AsyncRebateCancelAcceptReqDTO request) {
		return new Response<>(null);
	}

	@Override
	public Response<AsyncRebateTradeQueryResDTO> query(AsyncRebateTradeQueryReqDTO request) {
		return new Response<>(null);
	}
}
