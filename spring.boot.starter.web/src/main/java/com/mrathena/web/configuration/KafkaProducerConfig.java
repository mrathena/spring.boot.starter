package com.mrathena.web.configuration;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.ProducerListener;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mrathena on 2019/11/8 19:57
 */
@Configuration
public class KafkaProducerConfig {

	@Value("${spring.kafka.bootstrap.servers.group}")
	private String groupKafkaBootstrapServers;
	@Value("${spring.kafka.bootstrap.servers.tech}")
	private String techKafkaBootstrapServers;
	@Value("${spring.kafka.bootstrap.servers.v3}")
	private String v3KafkaBootstrapServers;

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
