package com.mrathena.remote.customer;

import cn.com.bestpay.Response;
import com.bestpay.cif.product.bizparammodel.CustomerResultDto;
import com.bestpay.cif.product.bizparammodel.account.AccountInfoDto;
import com.bestpay.cif.product.enummodel.KeyType;
import com.mrathena.common.constant.Constant;
import com.mrathena.common.exception.RemoteServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author mrathena on 2020-03-29 05:52
 */
@Slf4j
@Component
public class CustomerFacade {

	@Resource
	private CustomerIntegration integration;

	private static final String CUSTOMER_NOT_EXIST_CODE = "404010";

	public CustomerResultDto queryCustomer(String mobile) {
		try {
			AccountInfoDto request = new AccountInfoDto();
			request.setQueryKeyValue(mobile);
			request.setQueryKeyType(KeyType.PRODUCT.getCode());
			Response<CustomerResultDto> response = integration.queryCustomerInfoAndGrade(request);
			if (!response.isSuccess()) {
				// 404010 : 亲，用户不存在，请重新输入或注册新账号
				if (CUSTOMER_NOT_EXIST_CODE.equals(response.getErrorCode())) {
					return null;
				}
				String message = response.getErrorCode() + Constant.COLON + response.getErrorMsg();
				throw new RemoteServiceException(message);
			}
			return response.getResult();
		} catch (Throwable throwable) {
			throw new RemoteServiceException(throwable);
		}
	}

}
