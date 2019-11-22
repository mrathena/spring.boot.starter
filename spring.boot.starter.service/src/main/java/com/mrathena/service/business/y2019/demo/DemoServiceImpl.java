package com.mrathena.service.business.y2019.demo;

import com.bestpay.dubbo.result.Result;
import com.mrathena.biz.business.y2019.demo.DemoBiz;
import com.mrathena.common.constant.Constant;
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
	public Result<Boolean> createDemo(CreateDemoReqDTO request) {
		LogKit.setTraceNo(request.getTraceNo());
		long begin = System.currentTimeMillis();
		try {
			log.info("REQUEST:{}", request);
			Result<Boolean> response = new Result<>(demoBiz.createDemo(request));
			long interval = System.currentTimeMillis() - begin;
			log.info("[{}ms][SUCCESS][SUCCESS] REQUEST:{} RESPONSE:{}", interval, request, response);
			return response;
		} catch (Exception e) {
			Result<Boolean> response = ExceptionHandler.handleBusinessException(e);
			long interval = System.currentTimeMillis() - begin;
			String status = ExceptionHandler.isNormalBlockingException(e) ? Constant.SUCCESS : Constant.EXCEPTION;
			String codeMsg = response.getErrorCode() + Constant.COLON + response.getErrorMsg();
			String message = ExceptionHandler.getStackTrace(e);
			log.info("[{}ms][{}][{}] REQUEST:{} EXCEPTION:{}", interval, status, codeMsg, request, message);
			log.error("[{}ms][{}][{}] REQUEST:{} EXCEPTION:", interval, status, codeMsg, request, e);
			return response;
		} finally {
			MDC.clear();
		}
	}

	@Override
	public Result<QueryDemoResDTO> queryDemo(QueryDemoReqDTO request) {
		LogKit.setTraceNo(request.getTraceNo());
		long begin = System.currentTimeMillis();
		try {
			log.info("REQUEST:{}", request);
			Result<QueryDemoResDTO> response = new Result<>(demoBiz.queryDemo(request));
			long interval = System.currentTimeMillis() - begin;
			log.info("[{}ms][SUCCESS][SUCCESS] REQUEST:{} RESPONSE:{}", interval, request, response);
			return response;
		} catch (Exception e) {
			Result<QueryDemoResDTO> response = ExceptionHandler.handleBusinessException(e);
			long interval = System.currentTimeMillis() - begin;
			String status = ExceptionHandler.isNormalBlockingException(e) ? Constant.SUCCESS : Constant.EXCEPTION;
			String codeMsg = response.getErrorCode() + Constant.COLON + response.getErrorMsg();
			String message = ExceptionHandler.getStackTrace(e);
			log.info("[{}ms][{}][{}] REQUEST:{} EXCEPTION:{}", interval, status, codeMsg, request, message);
			log.error("[{}ms][{}][{}] REQUEST:{} EXCEPTION:", interval, status, codeMsg, request, e);
			return response;
		} finally {
			MDC.clear();
		}
	}

	@Override
	public Result<BasePageResDTO<DemoDTO>> queryDemoWithPage(QueryDemoReqDTO request) {
		LogKit.setTraceNo(request.getTraceNo());
		long begin = System.currentTimeMillis();
		try {
			log.info("REQUEST:{}", request);
			Result<BasePageResDTO<DemoDTO>> response = new Result<>(demoBiz.queryDemoWithPage(request));
			long interval = System.currentTimeMillis() - begin;
			log.info("[{}ms][SUCCESS][SUCCESS] REQUEST:{} RESPONSE:{}", interval, request, response);
			return response;
		} catch (Exception e) {
			Result<BasePageResDTO<DemoDTO>> response = ExceptionHandler.handleBusinessException(e);
			long interval = System.currentTimeMillis() - begin;
			String status = ExceptionHandler.isNormalBlockingException(e) ? Constant.SUCCESS : Constant.EXCEPTION;
			String codeMsg = response.getErrorCode() + Constant.COLON + response.getErrorMsg();
			String message = ExceptionHandler.getStackTrace(e);
			log.info("[{}ms][{}][{}] REQUEST:{} EXCEPTION:{}", interval, status, codeMsg, request, message);
			log.error("[{}ms][{}][{}] REQUEST:{} EXCEPTION:", interval, status, codeMsg, request, e);
			return response;
		} finally {
			MDC.clear();
		}
	}
}
