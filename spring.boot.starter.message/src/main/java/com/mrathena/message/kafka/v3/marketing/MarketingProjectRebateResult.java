package com.mrathena.message.kafka.v3.marketing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author mrathena on 2019/11/8 19:48
 */
@Slf4j
@Component
public class MarketingProjectRebateResult {

//	@KafkaListener(topics = "marketing_project_rebate_result",
//			containerFactory = "v3KafkaListenerContainerFactory")
//	public void tenantKafkaCustomer(ConsumerRecord<String, String> record) {
//		LogKit.setTraceNo(IdKit.getUuid());
//		log.info("KAFKA:CONSUMER:MESSAGE:{}", record.value());
//	}

}
