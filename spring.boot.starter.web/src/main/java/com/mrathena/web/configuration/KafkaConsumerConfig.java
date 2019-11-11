package com.mrathena.web.configuration;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mrathena on 2019/11/8 16:05
 */
@Configuration
public class KafkaConsumerConfig {

	private static final String GROUP_ID = "spring.boot.starter";

	@Value("${spring.kafka.bootstrap.servers.hx}")
	private String hxKafkaBootstrapServers;
	@Value("${spring.kafka.bootstrap.servers.tech}")
	private String techKafkaBootstrapServers;
	@Value("${spring.kafka.bootstrap.servers.v3}")
	private String v3KafkaBootstrapServers;

	@Bean(name = "hxKafkaListenerContainerFactory")
	KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer, String>> hxKafkaListenerContainerFactory() {
		// ConsumerConfig
		Map<String, Object> config = new HashMap<>(8);
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, hxKafkaBootstrapServers);
		// 消费者组
		config.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
		// 消费端请配置自动提交，并做好业务上的幂等
		config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
		// 若消费逻辑比较耗时，则可减少每次最大拉取消息的数量，调低max.poll.records的值
		// 例如max.poll.records=10，改值默认需要300秒内单线程处理完成，请根据业务耗时自行设置合理的值
		config.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 10);
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		// ConcurrentKafkaListenerContainerFactory
		ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(config));
		factory.setConcurrency(3);
		factory.getContainerProperties().setPollTimeout(3000);
		return factory;
	}

	@Bean(name = "techKafkaListenerContainerFactory")
	KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer, String>> techKafkaListenerContainerFactory() {
		// ConsumerConfig
		Map<String, Object> config = new HashMap<>(8);
		config.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, techKafkaBootstrapServers);
		config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
		config.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 10);
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		// ConcurrentKafkaListenerContainerFactory
		ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(config));
		factory.setConcurrency(3);
		factory.getContainerProperties().setPollTimeout(3000);
		return factory;
	}

	@Bean(name = "v3KafkaListenerContainerFactory")
	KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer, String>> v3KafkaListenerContainerFactory() {
		// ConsumerConfig
		Map<String, Object> config = new HashMap<>(8);
		config.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, v3KafkaBootstrapServers);
		config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
		config.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 10);
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		// ConcurrentKafkaListenerContainerFactory
		ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(config));
		factory.setConcurrency(3);
		factory.getContainerProperties().setPollTimeout(3000);
		return factory;
	}

}
