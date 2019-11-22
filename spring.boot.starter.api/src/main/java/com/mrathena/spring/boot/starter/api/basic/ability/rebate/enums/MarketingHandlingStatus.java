package com.mrathena.spring.boot.starter.api.basic.ability.rebate.enums;

/**
 * @author mrathena on 2019/11/22 11:04
 */
public enum MarketingHandlingStatus {

	/**
	 * SUCCEED:处理成功(终态)
	 * FAILURE:处理失败(终态)
	 * PENDING:处理过程异常(非终态,异步通知的话后续还可能有同一笔交易的终态通知)
	 */
	SUCCEED, FAILURE, PENDING

}
