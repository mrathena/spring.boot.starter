package com.mrathena.service.business.y2019.demo;

import com.mrathena.biz.business.y2019.demo.DemoBiz;
import com.mrathena.common.constant.Constant;
import com.mrathena.common.entity.Response;
import com.mrathena.common.exception.ExceptionHandler;
import com.mrathena.common.toolkit.LogKit;
import com.mrathena.spring.boot.starter.api.BasePageResDTO;
import com.mrathena.spring.boot.starter.api.business.y2019.demo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.MDC;
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
		LogKit.setTraceNo(request.getTraceNo());
		long begin = System.currentTimeMillis();
		try {
			log.info("REQUEST:{}", request);
			Response<Boolean> response = new Response<>(demoBiz.createDemo(request));
			long interval = System.currentTimeMillis() - begin;
			log.info("[{}ms][SUCCESS][SUCCESS] REQUEST:{} RESPONSE:{}", interval, request, response);
			return response;
		} catch (Exception e) {
			Response<Boolean> response = ExceptionHandler.handleBizException(e);
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
	public Response<QueryDemoResDTO> queryDemo(QueryDemoReqDTO request) {
		LogKit.setTraceNo(request.getTraceNo());
		long begin = System.currentTimeMillis();
		try {
			log.info("REQUEST:{}", request);
			Response<QueryDemoResDTO> response = new Response<>(demoBiz.queryDemo(request));
			long interval = System.currentTimeMillis() - begin;
			log.info("[{}ms][SUCCESS][SUCCESS] REQUEST:{} RESPONSE:{}", interval, request, response);
			return response;
		} catch (Exception e) {
			Response<QueryDemoResDTO> response = ExceptionHandler.handleBizException(e);
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
	public Response<BasePageResDTO<DemoDTO>> queryDemoWithPage(QueryDemoReqDTO request) {
		LogKit.setTraceNo(request.getTraceNo());
		long begin = System.currentTimeMillis();
		try {
			log.info("REQUEST:{}", request);
			Response<BasePageResDTO<DemoDTO>> response = new Response<>(demoBiz.queryDemoWithPage(request));
			long interval = System.currentTimeMillis() - begin;
			log.info("[{}ms][SUCCESS][SUCCESS] REQUEST:{} RESPONSE:{}", interval, request, response);
			return response;
		} catch (Exception e) {
			Response<BasePageResDTO<DemoDTO>> response = ExceptionHandler.handleBizException(e);
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
