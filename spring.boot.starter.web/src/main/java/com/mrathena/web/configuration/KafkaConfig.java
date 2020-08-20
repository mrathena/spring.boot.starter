package com.mrathena.web.configuration;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.ProducerListener;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mrathena on 2019/11/8 16:05
 */
@Configuration
public class KafkaConfig {

	private static final String GROUP_ID = "spring.boot.starter";

	@Value("${spring.kafka.bootstrap.servers.group}")
	private String groupKafkaBootstrapServers;
	@Value("${spring.kafka.bootstrap.servers.tech}")
	private String techKafkaBootstrapServers;
	@Value("${spring.kafka.bootstrap.servers.v3}")
	private String v3KafkaBootstrapServers;

	@Bean(name = "groupKafkaListenerContainerFactory")
	KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer, String>> groupKafkaListenerContainerFactory() {
		// ConsumerConfig
		Map<String, Object> config = new HashMap<>(8);
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, groupKafkaBootstrapServers);
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
		// 这个并发量根据分区数决定
		// 一个消费线程可以对应若干个分区,但一个分区只能被具体某一个消费线程消费
		// 陈苏强: 应用节点数*concurrency>=队列分区数, 保证每个分区都有一个消费线程消费, 不要让一个线程消费多个分区
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

	@Bean(name = "groupKafkaTemplate")
	public KafkaTemplate<String, String> groupKafkaTemplate(ProducerListener<String, String> kafkaProducerListener) {
		// ProducerConfig
		Map<String, Object> config = new HashMap<>(8);
		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, groupKafkaBootstrapServers);
		// acks表示所有需同步返回确认的节点数，all或者‑1表示分区全部备份节点均需响应，可靠性最高，但吞吐量会相对降低；
		// 1表示只需分区leader节点响应；0表示无需等待服务端响应；大部分业务建议配置1，风控或安全建议配置0
		config.put(ProducerConfig.ACKS_CONFIG, "1");
		// retries表示重试次数，如果配置重试请保证消费端具有业务上幂等，根据业务需求配置
		config.put(ProducerConfig.RETRIES_CONFIG, 0);
		// 发送消息请求的超时时间，规范2000
		config.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 2000);
		// 如果发送方buffer满或者获取不到元数据时最大阻塞时间，规范2000
		config.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 2000);
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		// KafkaTemplate
		// autoFlush=true表示与kafka同步交互，可靠性高但吞吐量较差，核心场景可以配置true，一般设为false
		KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(config), false);
		kafkaTemplate.setProducerListener(kafkaProducerListener);
		return kafkaTemplate;
	}

	@Bean(name = "techKafkaTemplate")
	public KafkaTemplate<String, String> techKafkaTemplate(ProducerListener<String, String> kafkaProducerListener) {
		// ProducerConfig
		Map<String, Object> config = new HashMap<>(8);
		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, techKafkaBootstrapServers);
		config.put(ProducerConfig.ACKS_CONFIG, "1");
		config.put(ProducerConfig.RETRIES_CONFIG, 0);
		config.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 2000);
		config.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 2000);
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(config), false);
		kafkaTemplate.setProducerListener(kafkaProducerListener);
		return kafkaTemplate;
	}

	@Bean(name = "v3KafkaTemplate")
	public KafkaTemplate<String, String> v3KafkaTemplate(ProducerListener<String, String> kafkaProducerListener) {
		// ProducerConfig
		Map<String, Object> config = new HashMap<>(8);
		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, v3KafkaBootstrapServers);
		config.put(ProducerConfig.ACKS_CONFIG, "1");
		config.put(ProducerConfig.RETRIES_CONFIG, 0);
		config.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 2000);
		config.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 2000);
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(config), false);
		kafkaTemplate.setProducerListener(kafkaProducerListener);
		return kafkaTemplate;
	}

}
