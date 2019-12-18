package com.mrathena.task;

import com.bestpay.redbag.core.entity.kafka.AsyncRebateRequestMsgDTO;
import com.mrathena.biz.toolkit.Kafka;
import com.mrathena.common.toolkit.IdKit;
import com.mrathena.common.toolkit.LogKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author mrathena on 2019/11/6 22:05
 */
@Slf4j
@Component
public class KafkaTask {

	@Resource
	private Kafka kafka;

	@Scheduled(cron = "0/10 * * * * ?")
	public void kafkaTest() {
		LogKit.setTraceNo(IdKit.getUuid());
		AsyncRebateRequestMsgDTO message = new AsyncRebateRequestMsgDTO();
		message.setUniqueNo(IdKit.getSerialNo());
		message.setMarketCfgId("marketCfgId").setMerchantNo("merchantNo").setOutTxnType("outTxnType");
		message.setTxnChannel("txnChannel").setTxnAmount(1L).setProductNo("18234089811").setRequestAt(new Date());
//		kafka.sendGroup("redbag_product_grab_coupon_request", message);
	}

}
