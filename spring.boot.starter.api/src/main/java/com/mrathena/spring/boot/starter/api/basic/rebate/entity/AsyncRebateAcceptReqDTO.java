package com.mrathena.spring.boot.starter.api.basic.rebate.entity;

import com.mrathena.spring.boot.starter.api.BaseReqDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author mrathena on 2019/11/22 10:03
 */
@Getter
@Setter
@Accessors(chain = true)
@ToString(callSuper = true)
public class AsyncRebateAcceptReqDTO extends BaseReqDTO {

	/**
	 * 业务号, 用于区分不通业务
	 */
	private String businessNo;
	private String businessName;

	/**
	 * 关联号, 用于关联每一笔业务
	 */
	private String relationNo;

	/**
	 * 返利请求参数
	 */
	private String productNo;
	private String activityNo;
	private String merchantNo;
	private String outTradeType;
	private String channel;
	private Long amount;

}
