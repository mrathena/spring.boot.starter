package com.mrathena.service.y2019.demo;

import com.mrathena.biz.y2019.demo.DemoBiz;
import com.mrathena.common.constant.Constant;
import com.mrathena.common.entity.Response;
import com.mrathena.common.exception.ExceptionHandler;
import com.mrathena.common.toolkit.IdKit;
import com.mrathena.spring.boot.starter.api.y2019.demo.DemoReqDto;
import com.mrathena.spring.boot.starter.api.y2019.demo.DemoResDto;
import com.mrathena.spring.boot.starter.api.y2019.demo.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author mrathena on 2019/10/17 21:58
 */
@Slf4j
//@Service(version = "1.0.0")
public class DemoServiceImpl implements DemoService {

	@Autowired
	private DemoBiz demoBiz;

	/**
	 * demoMethod
	 */
	@Override
	public Response<DemoResDto> demoMethod(DemoReqDto request) {
		MDC.put(Constant.TRACE, request.getTraceNo());
		long begin = System.currentTimeMillis();
		try {
			log.info("[REQUEST:{}]", request);
			Response<DemoResDto> response = new Response<>(demoBiz.demoMethod(request));
			long interval = System.currentTimeMillis() - begin;
			log.info("[SUCCESS:{}ms], [REQUEST:{}], [RESPONSE:{}]", interval, request, response);
			return response;
		} catch (Exception e) {
			long interval = System.currentTimeMillis() - begin;
			String message = ExceptionHandler.getClassAndMessageWithoutCustomizedException(e);
			log.info("[EXCEPTION:{}ms], [REQUEST:{}], [MESSAGE:{}]", interval, request, message);
			log.error("[EXCEPTION:{}ms], [REQUEST:{}], [MESSAGE:{}]", interval, request, message, e);
			return ExceptionHandler.handleBizException(e);
		} finally {
			MDC.clear();
		}
	}

	/**
	 * demoMethod2
	 */
	@Override
	public Response<String> demoMethod2(String request) {
		MDC.put(Constant.TRACE, IdKit.getSerialNo());
		long begin = System.currentTimeMillis();
		try {
			log.info("[REQUEST:{}]", request);
			Response<String> response = new Response<>(request);
			long interval = System.currentTimeMillis() - begin;
			log.info("[SUCCESS:{}ms], [REQUEST:{}], [RESPONSE:{}]", interval, request, response);
			return new Response<>(request);
		} catch (Exception e) {
			long interval = System.currentTimeMillis() - begin;
			String message = ExceptionHandler.getClassAndMessageWithoutCustomizedException(e);
			log.info("[EXCEPTION:{}ms], [REQUEST:{}], [MESSAGE:{}]", interval, request, message);
			log.error("[EXCEPTION:{}ms], [REQUEST:{}], [MESSAGE:{}]", interval, request, message, e);
			return ExceptionHandler.handleBizException(e);
		} finally {
			MDC.clear();
		}
	}
}
