package com.mrathena.service.y2019.demo;

import com.bestpay.dubbo.result.Result;
import com.mrathena.biz.y2019.demo.DemoBiz;
import com.mrathena.common.constant.Constant;
import com.mrathena.common.exception.ExceptionHandler;
import com.mrathena.common.toolkit.LogKit;
import com.mrathena.spring.boot.starter.api.BasePageResDto;
import com.mrathena.spring.boot.starter.api.y2019.demo.*;
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
	public Result<Boolean> createDemo(CreateDemoReqDto request) {
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
	public Result<QueryDemoResDto> queryDemo(QueryDemoReqDto request) {
		LogKit.setTraceNo(request.getTraceNo());
		long begin = System.currentTimeMillis();
		try {
			log.info("REQUEST:{}", request);
			Result<QueryDemoResDto> response = new Result<>(demoBiz.queryDemo(request));
			long interval = System.currentTimeMillis() - begin;
			log.info("[{}ms][SUCCESS][SUCCESS] REQUEST:{} RESPONSE:{}", interval, request, response);
			return response;
		} catch (Exception e) {
			Result<QueryDemoResDto> response = ExceptionHandler.handleBusinessException(e);
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
	public Result<BasePageResDto<DemoDto>> queryDemoWithPage(QueryDemoReqDto request) {
		LogKit.setTraceNo(request.getTraceNo());
		long begin = System.currentTimeMillis();
		try {
			log.info("REQUEST:{}", request);
			Result<BasePageResDto<DemoDto>> response = new Result<>(demoBiz.queryDemoWithPage(request));
			long interval = System.currentTimeMillis() - begin;
			log.info("[{}ms][SUCCESS][SUCCESS] REQUEST:{} RESPONSE:{}", interval, request, response);
			return response;
		} catch (Exception e) {
			Result<BasePageResDto<DemoDto>> response = ExceptionHandler.handleBusinessException(e);
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
