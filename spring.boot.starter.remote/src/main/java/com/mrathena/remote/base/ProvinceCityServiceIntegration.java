package com.mrathena.remote.base;

import com.bestpay.basic.service.ProvinceService;
import com.bestpay.basic.service.request.BasicRequest;
import com.bestpay.basic.service.request.QueryCityRequest;
import com.bestpay.basic.service.response.BasicResponse;
import com.bestpay.basic.service.response.QueryCityResponse;
import com.bestpay.basic.service.response.QueryProvinceResponse;
import com.mrathena.common.constant.Constant;
import com.mrathena.common.exception.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author mrathena on 2019/10/22 0:50
 */
@Slf4j
@Component
public class ProvinceCityServiceIntegration {

	@Reference
	private ProvinceService service;

	public BasicResponse<List<QueryProvinceResponse>> queryProvinceList(BasicRequest request) {
		long begin = System.currentTimeMillis();
		try {
			log.info("REMOTE:REQUEST:{}", request);
			BasicResponse<List<QueryProvinceResponse>> response = service.queryAllProvince(request);
			long interval = System.currentTimeMillis() - begin;
			String status = response.isSuccess() ? Constant.SUCCESS : response.getErrorCode() + Constant.COLON + response.getErrorMsg();
			log.info("[{}ms][SUCCESS][{}] REMOTE:REQUEST:{} REMOTE:RESPONSE:{}", interval, status, request, response);
			return response;
		} catch (Exception e) {
			long interval = System.currentTimeMillis() - begin;
			String message = ExceptionHandler.getStackTrace(e);
			log.info("[{}ms][EXCEPTION][NONE] REMOTE:REQUEST:{} REMOTE:EXCEPTION:{}", interval, request, message);
			log.error("[{}ms][EXCEPTION][NONE] REMOTE:REQUEST:{} REMOTE:EXCEPTION:", interval, request, e);
			throw e;
		}
	}

	public BasicResponse<List<QueryCityResponse>> queryCityListByProvinceCode(QueryCityRequest request) {
		long begin = System.currentTimeMillis();
		try {
			log.info("REMOTE:REQUEST:{}", request);
			BasicResponse<List<QueryCityResponse>> response = service.queryCityByPrivinceCode(request);
			long interval = System.currentTimeMillis() - begin;
			String status = response.isSuccess() ? Constant.SUCCESS : response.getErrorCode() + Constant.COLON + response.getErrorMsg();
			log.info("[{}ms][SUCCESS][{}] REMOTE:REQUEST:{} REMOTE:RESPONSE:{}", interval, status, request, response);
			return response;
		} catch (Exception e) {
			long interval = System.currentTimeMillis() - begin;
			String message = ExceptionHandler.getStackTrace(e);
			log.info("[{}ms][EXCEPTION][NONE] REMOTE:REQUEST:{} REMOTE:EXCEPTION:{}", interval, request, message);
			log.error("[{}ms][EXCEPTION][NONE] REMOTE:REQUEST:{} REMOTE:EXCEPTION:", interval, request, e);
			throw e;
		}
	}

}
