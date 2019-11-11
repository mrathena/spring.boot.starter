package com.mrathena.message.kafka.consumer.group.redbag;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author mrathena on 2019/11/8 16:34
 */
@Slf4j
@Component
public class RedbagProductGrabCouponRequest {

	/*@KafkaListener(topics = "redbag_product_grab_coupon_request",
			containerFactory = "groupKafkaListenerContainerFactory")
	public void tenantKafkaCustomer(ConsumerRecord<String, String> record) {
		LogKit.setTraceNo(IdKit.getUuid());
		log.info("KAFKA:CONSUMER:MESSAGE:{}", record.value());
	}*/

}
