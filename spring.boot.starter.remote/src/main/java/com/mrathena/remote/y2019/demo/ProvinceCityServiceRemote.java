package com.mrathena.remote.y2019.demo;

import com.bestpay.basic.service.request.BasicRequest;
import com.bestpay.basic.service.request.QueryCityRequest;
import com.bestpay.basic.service.response.BasicResponse;
import com.bestpay.basic.service.response.QueryCityResponse;
import com.bestpay.basic.service.response.QueryProvinceResponse;
import com.mrathena.common.exception.RemoteServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author mrathena on 2019/11/2 16:54
 */
@Slf4j
@Component
public class ProvinceCityServiceRemote {

	@Resource
	private ProvinceCityServiceIntegration integration;

	public List<QueryProvinceResponse> queryProvinceList() {
		try {
			BasicResponse<List<QueryProvinceResponse>> response = integration.queryProvinceList(new BasicRequest());
			if (null == response) {
				throw new RemoteServiceException("系统异常", "远程服务无响应");
			}
			List<QueryProvinceResponse> result = response.getResult();
			if (CollectionUtils.isEmpty(result)) {
				throw new RemoteServiceException("系统异常", "远程服务响应中没有数据");
			}
			return result;
		} catch (Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	public List<QueryCityResponse> queryCityListByProvinceCode(String provinceCode) {
		try {
			QueryCityRequest request = new QueryCityRequest();
			request.setProviceCode(provinceCode);
			BasicResponse<List<QueryCityResponse>> response = integration.queryCityListByProvinceCode(request);
			if (null == response) {
				throw new RemoteServiceException("系统异常", "远程服务无响应");
			}
			List<QueryCityResponse> result = response.getResult();
			if (CollectionUtils.isEmpty(result)) {
				throw new RemoteServiceException("系统异常", "远程服务响应中没有数据");
			}
			return result;
		} catch (Exception e) {
			throw new RemoteServiceException(e);
		}
	}

}
