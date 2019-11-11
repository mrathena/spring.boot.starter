package com.mrathena.message.kafka.producer;

import com.mrathena.common.exception.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;

/**
 * @author mrathena on 2019/5/30 10:32
 */
@Slf4j
@Component
public class KafkaProducerListener implements ProducerListener<String, String> {

	@Override
	public void onSuccess(String topic, Integer partition, String key, String value, RecordMetadata recordMetadata) {
		log.info("KAFKA:PRODUCER:SEND:SUCCESS topic:{} partition:{} key:{} value:{} recordMetadata:{}",
				topic, partition, key, value, recordMetadata);
	}

	@Override
	public void onError(String topic, Integer partition, String key, String value, Exception exception) {
		String description = ExceptionHandler.getStackTrace(exception);
		log.info("KAFKA:PRODUCER:SEND:ERROR topic:{} partition:{} key:{} value:{} exception:{}",
				topic, partition, key, value, description);
		log.error("KAFKA:PRODUCER:SEND:ERROR topic:{} partition:{} key:{} value:{} exception:",
				topic, partition, key, value, exception);
	}

}
