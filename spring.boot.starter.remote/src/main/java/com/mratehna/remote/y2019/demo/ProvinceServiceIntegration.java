package com.mratehna.remote.y2019.demo;

import com.bestpay.basic.service.ProvinceService;
import com.bestpay.basic.service.request.BasicRequest;
import com.bestpay.basic.service.request.QueryCityRequest;
import com.bestpay.basic.service.response.BasicResponse;
import com.bestpay.basic.service.response.QueryCityResponse;
import com.bestpay.basic.service.response.QueryProvinceResponse;
import com.mrathena.common.exception.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Method;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author mrathena on 2019/10/22 0:50
 */
@Slf4j
@Component
public class ProvinceServiceIntegration {

	@Reference(interfaceClass = ProvinceService.class,
			version = "1.0.0",
			timeout = 1000,
			methods = {
					@Method(name = "queryAllProvince", timeout = 3000, retries = 0),
					@Method(name = "queryCityByPrivinceCode", timeout = 3000, retries = 0)
			})
	private ProvinceService service;

	public BasicResponse<List<QueryProvinceResponse>> queryAllProvince(BasicRequest request) {
		long begin = System.currentTimeMillis();
		try {
			log.info("[REMOTE:REQUEST:{}]", request);
			BasicResponse<List<QueryProvinceResponse>> response = service.queryAllProvince(request);
			long interval = System.currentTimeMillis() - begin;
			log.info("[REMOTE:SUCCESS:{}ms], [REQUEST:{}], [RESPONSE:{}]", interval, request, response);
			return response;
		} catch (Exception e) {
			long interval = System.currentTimeMillis() - begin;
			String message = ExceptionHandler.getClassAndMessageWithoutCustomizedException(e);
			log.info("[REMOTE:EXCEPTION:{}ms], [REQUEST:{}], [MESSAGE:{}]", interval, request, message);
			log.error("[REMOTE:EXCEPTION:{}ms], [REQUEST:{}], [MESSAGE:{}]", interval, request, message, e);
			throw e;
		}
	}

	public BasicResponse<List<QueryCityResponse>> queryCityByProvinceCode(QueryCityRequest request) {
		long begin = System.currentTimeMillis();
		try {
			log.info("[REMOTE:REQUEST:{}]", request);
			BasicResponse<List<QueryCityResponse>> response = service.queryCityByPrivinceCode(request);
			long interval = System.currentTimeMillis() - begin;
			log.info("[REMOTE:SUCCESS:{}ms], [REQUEST:{}], [RESPONSE:{}]", interval, request, response);
			return response;
		} catch (Exception e) {
			long interval = System.currentTimeMillis() - begin;
			String message = ExceptionHandler.getClassAndMessageWithoutCustomizedException(e);
			log.info("[REMOTE:EXCEPTION:{}ms], [REQUEST:{}], [MESSAGE:{}]", interval, request, message);
			log.error("[REMOTE:EXCEPTION:{}ms], [REQUEST:{}], [MESSAGE:{}]", interval, request, message, e);
			throw e;
		}
	}

}
