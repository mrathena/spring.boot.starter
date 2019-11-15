package com.mrathena.message.kafka.group.redbag;

import com.mrathena.common.toolkit.IdKit;
import com.mrathena.common.toolkit.LogKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author mrathena on 2019/11/8 16:34
 */
@Slf4j
@Component
public class RedbagProductGrabCouponRequest {

	@KafkaListener(topics = "redbag_product_grab_coupon_request",
			containerFactory = "groupKafkaListenerContainerFactory")
	public void tenantKafkaCustomer(ConsumerRecord<String, String> record) {
		LogKit.setTraceNo(IdKit.getUuid());
		log.info("KAFKA:CONSUMER:MESSAGE:{}", record);
	}

}
