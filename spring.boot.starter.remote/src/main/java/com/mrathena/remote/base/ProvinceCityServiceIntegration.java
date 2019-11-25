package com.mrathena.remote.base;

import com.bestpay.basic.service.ProvinceService;
import com.bestpay.basic.service.request.BasicRequest;
import com.bestpay.basic.service.request.QueryCityRequest;
import com.bestpay.basic.service.response.BasicResponse;
import com.bestpay.basic.service.response.QueryCityResponse;
import com.bestpay.basic.service.response.QueryProvinceResponse;
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
		return service.queryAllProvince(request);
	}

	public BasicResponse<List<QueryCityResponse>> queryCityListByProvinceCode(QueryCityRequest request) {
		return service.queryCityByPrivinceCode(request);
	}

}
