package com.mrathena.task;

import com.mrathena.biz.toolkit.Kafka;
import com.mrathena.common.toolkit.IdKit;
import com.mrathena.common.toolkit.LogKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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
//		kafka.sendGroup("redbag_product_grab_coupon_request", message);
	}

}
