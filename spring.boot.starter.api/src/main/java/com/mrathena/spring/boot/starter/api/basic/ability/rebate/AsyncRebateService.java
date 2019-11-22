package com.mrathena.spring.boot.starter.api.basic.ability.rebate;

import com.mrathena.common.entity.Response;
import com.mrathena.spring.boot.starter.api.basic.ability.rebate.entity.*;

/**
 * @author mrathena on 2019/11/22 10:01
 */
public interface AsyncRebateService {

	/**
	 * 异步返利受理接口
	 *
	 * @param request .
	 * @return .
	 */
	Response<AsyncRebateAcceptResDTO> rebate(AsyncRebateAcceptReqDTO request);

	/**
	 * 返利撤销受理接口
	 *
	 * @param request .
	 * @return .
	 */
	Response<AsyncRebateCancelAcceptResDTO> cancel(AsyncRebateCancelAcceptReqDTO request);

	/**
	 * 返利交易查询接口
	 *
	 * @param request .
	 * @return .
	 */
	Response<AsyncRebateTradeQueryResDTO> query(AsyncRebateTradeQueryReqDTO request);

}
