package com.mrathena.biz.toolkit;

import com.google.gson.Gson;
import com.mrathena.common.exception.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author mrathena on 2019/11/14 10:42
 */
@Slf4j
@Component
public class Kafka {

	@Resource(name = "groupKafkaTemplate")
	private KafkaTemplate<String, String> groupKafkaTemplate;
	@Resource(name = "techKafkaTemplate")
	private KafkaTemplate<String, String> techKafkaTemplate;
	@Resource(name = "v3KafkaTemplate")
	private KafkaTemplate<String, String> v3KafkaTemplate;

	@Resource
	private Gson gson;

	/**
	 * 向划小事业群kafka发送消息
	 *
	 * @param topic  .
	 * @param object .
	 */
	public void sendGroup(String topic, Object object) {
		groupKafkaTemplate.send(topic, gson.toJson(object));
	}

	/**
	 * 向技术部kafka发送消息
	 *
	 * @param topic  .
	 * @param object .
	 */
	public void sendTech(String topic, Object object) {
		techKafkaTemplate.send(topic, gson.toJson(object));
	}

	/**
	 * 向3.0kafka发送消息
	 *
	 * @param topic  .
	 * @param object .
	 */
	public void sendV3(String topic, Object object) {
		v3KafkaTemplate.send(topic, gson.toJson(object));
	}

	@Slf4j
	@Component
	static class KafkaProducerListener implements ProducerListener<String, String> {

		@Override
		public void onSuccess(String topic, Integer partition, String key, String value, RecordMetadata recordMetadata) {
			log.info("KAFKA:PRODUCER:SEND:SUCCESS topic:{} partition:{} key:{} value:{} recordMetadata:{}",
					topic, partition, key, value, recordMetadata);
		}

		@Override
		public void onError(String topic, Integer partition, String key, String value, Exception exception) {
			String description = ExceptionHandler.getStackTraceStr(exception);
			log.info("KAFKA:PRODUCER:SEND:ERROR topic:{} partition:{} key:{} value:{} exception:{}",
					topic, partition, key, value, description);
			log.error("KAFKA:PRODUCER:SEND:ERROR topic:{} partition:{} key:{} value:{} exception:",
					topic, partition, key, value, exception);
		}

	}

}