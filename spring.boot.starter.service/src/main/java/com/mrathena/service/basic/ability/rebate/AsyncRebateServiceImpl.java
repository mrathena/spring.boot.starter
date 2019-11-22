package com.mrathena.service.basic.ability.rebate;

import com.mrathena.common.constant.Constant;
import com.mrathena.common.entity.Response;
import com.mrathena.common.exception.ExceptionHandler;
import com.mrathena.common.toolkit.LogKit;
import com.mrathena.spring.boot.starter.api.basic.ability.rebate.AsyncRebateService;
import com.mrathena.spring.boot.starter.api.basic.ability.rebate.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

/**
 * @author mrathena on 2019/11/22 11:37
 */
@Slf4j
@Component
public class AsyncRebateServiceImpl implements AsyncRebateService {

	@Override
	public Response<AsyncRebateAcceptResDTO> rebate(AsyncRebateAcceptReqDTO request) {
		LogKit.setTraceNo(request.getTraceNo());
		long begin = System.currentTimeMillis();
		try {
			log.info("REQUEST:{}", request);
			Response<AsyncRebateAcceptResDTO> response = new Response<>(null);
			long interval = System.currentTimeMillis() - begin;
			log.info("[{}ms][SUCCESS][SUCCESS] REQUEST:{} RESPONSE:{}", interval, request, response);
			return response;
		} catch (Exception e) {
			Response<AsyncRebateAcceptResDTO> response = ExceptionHandler.handleBizException(e);
			long interval = System.currentTimeMillis() - begin;
			String status = ExceptionHandler.isNormalBlockingException(e) ? Constant.SUCCESS : Constant.EXCEPTION;
			String codeMsg = response.getCode() + Constant.COLON + response.getMessage();
			String message = ExceptionHandler.getStackTrace(e);
			log.info("[{}ms][{}][{}] REQUEST:{} EXCEPTION:{}", interval, status, codeMsg, request, message);
			log.error("[{}ms][{}][{}] REQUEST:{} EXCEPTION:", interval, status, codeMsg, request, e);
			return response;
		} finally {
			MDC.clear();
		}
	}

	@Override
	public Response<AsyncRebateCancelAcceptResDTO> cancel(AsyncRebateCancelAcceptReqDTO request) {
		LogKit.setTraceNo(request.getTraceNo());
		long begin = System.currentTimeMillis();
		try {
			log.info("REQUEST:{}", request);
			Response<AsyncRebateCancelAcceptResDTO> response = new Response<>(null);
			long interval = System.currentTimeMillis() - begin;
			log.info("[{}ms][SUCCESS][SUCCESS] REQUEST:{} RESPONSE:{}", interval, request, response);
			return response;
		} catch (Exception e) {
			Response<AsyncRebateCancelAcceptResDTO> response = ExceptionHandler.handleBizException(e);
			long interval = System.currentTimeMillis() - begin;
			String status = ExceptionHandler.isNormalBlockingException(e) ? Constant.SUCCESS : Constant.EXCEPTION;
			String codeMsg = response.getCode() + Constant.COLON + response.getMessage();
			String message = ExceptionHandler.getStackTrace(e);
			log.info("[{}ms][{}][{}] REQUEST:{} EXCEPTION:{}", interval, status, codeMsg, request, message);
			log.error("[{}ms][{}][{}] REQUEST:{} EXCEPTION:", interval, status, codeMsg, request, e);
			return response;
		} finally {
			MDC.clear();
		}
	}

	@Override
	public Response<AsyncRebateTradeQueryResDTO> query(AsyncRebateTradeQueryReqDTO request) {
		LogKit.setTraceNo(request.getTraceNo());
		long begin = System.currentTimeMillis();
		try {
			log.info("REQUEST:{}", request);
			Response<AsyncRebateTradeQueryResDTO> response = new Response<>(null);
			long interval = System.currentTimeMillis() - begin;
			log.info("[{}ms][SUCCESS][SUCCESS] REQUEST:{} RESPONSE:{}", interval, request, response);
			return response;
		} catch (Exception e) {
			Response<AsyncRebateTradeQueryResDTO> response = ExceptionHandler.handleBizException(e);
			long interval = System.currentTimeMillis() - begin;
			String status = ExceptionHandler.isNormalBlockingException(e) ? Constant.SUCCESS : Constant.EXCEPTION;
			String codeMsg = response.getCode() + Constant.COLON + response.getMessage();
			String message = ExceptionHandler.getStackTrace(e);
			log.info("[{}ms][{}][{}] REQUEST:{} EXCEPTION:{}", interval, status, codeMsg, request, message);
			log.error("[{}ms][{}][{}] REQUEST:{} EXCEPTION:", interval, status, codeMsg, request, e);
			return response;
		} finally {
			MDC.clear();
		}
	}
}
