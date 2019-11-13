package com.mrathena.message.kafka.consumer.v3.marketing;

import com.mrathena.common.toolkit.IdKit;
import com.mrathena.common.toolkit.LogKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author mrathena on 2019/11/8 19:48
 */
@Slf4j
@Component
public class MarketingProjectCancelResult {

	@KafkaListener(topics = "marketing_project_cancel_result",
			containerFactory = "v3KafkaListenerContainerFactory")
	public void tenantKafkaCustomer(ConsumerRecord<String, String> record) {
		LogKit.setTraceNo(IdKit.getUuid());
		log.info("KAFKA:CONSUMER:MESSAGE:{}", record.value());
	}

}
