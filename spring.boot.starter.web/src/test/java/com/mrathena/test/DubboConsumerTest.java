package com.mrathena.test;

import com.bestpay.basic.service.request.BasicRequest;
import com.bestpay.basic.service.request.QueryCityRequest;
import com.bestpay.basic.service.response.BasicResponse;
import com.bestpay.basic.service.response.QueryCityResponse;
import com.bestpay.basic.service.response.QueryProvinceResponse;
import com.mrathena.remote.base.ProvinceCityServiceIntegration;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author mrathena on 2019/10/22 1:24
 */
@Slf4j
public class DubboConsumerTest extends BaseTest {

	@Autowired
	private ProvinceCityServiceIntegration provinceServiceIntegration;

	@Test
	public void test() {
		BasicResponse<List<QueryProvinceResponse>> provinceResponse = provinceServiceIntegration.queryProvinceList(new BasicRequest());
		if (provinceResponse.isSuccess()) {
			List<QueryProvinceResponse> provinceResponseList = provinceResponse.getResult();
			for (QueryProvinceResponse province : provinceResponseList) {
				System.out.println(province);
			}
		}
		System.out.println("----------");
		QueryCityRequest queryCityRequest = new QueryCityRequest();
		queryCityRequest.setProviceCode("440000");
		BasicResponse<List<QueryCityResponse>> cityResponse = provinceServiceIntegration.queryCityListByProvinceCode(queryCityRequest);
		if (cityResponse.isSuccess()) {
			List<QueryCityResponse> cityResponseList = cityResponse.getResult();
			for (QueryCityResponse city : cityResponseList) {
				System.out.println(city);
			}
		}
	}

}
