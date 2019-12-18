package com.mrathena.web.configuration;

import com.mrathena.common.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author mrathena on 2019-10-17 00:28
 */
@Slf4j
@Configuration
public class RedisConfig {

	@Value("${spring.redis.sentinel.master}")
	private String redisSentinelMaster;
	@Value("${spring.redis.sentinel.nodes}")
	private String redisSentinelNodes;

	@Value("${spring.redis.cluster.nodes}")
	private String redisClusterNodes;

	@Bean
	public RedisSerializer<String> keySerializer() {
		return new StringRedisSerializer();
	}

	@Bean
	public RedisSerializer<Object> valueSerializer() {
		return new GenericJackson2JsonRedisSerializer();
	}

	@Bean
	public LettuceClientConfiguration lettuceClientConfiguration() {
		// GenericObjectPoolConfig
		GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
		genericObjectPoolConfig.setMaxTotal(100);
		genericObjectPoolConfig.setMaxIdle(100);
		genericObjectPoolConfig.setMinIdle(100);
		genericObjectPoolConfig.setTestOnCreate(true);
		genericObjectPoolConfig.setTestOnBorrow(false);
		genericObjectPoolConfig.setTestOnReturn(false);
		genericObjectPoolConfig.setTestWhileIdle(true);
		// TODO timeout=2000 不知道是什么配置, 原本是factory里面setTimeout用的, 但是现在已经过时了, 推荐放到LettuceClientConfiguration里设置
		// #客户端超时时间单位是毫秒,设置和read timeout 这个报错有关，当没有收到服务务器返回结果的时间超过这个设置时候，
		// 将报read timeout 这个报错，主动断开与服务器的连接的。参数依赖于实际应用开发需求。请根据实际需要配置。
		// redis.timeout=2000
		// LettucePoolingClientConfiguration
		LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder builder = LettucePoolingClientConfiguration.builder();
		LettucePoolingClientConfiguration lettucePoolingClientConfiguration = builder.poolConfig(genericObjectPoolConfig).build();
		log.info("Redis:lettuceClientConfiguration:初始化完成");
		return lettucePoolingClientConfiguration;
	}

	@Bean
	public LettuceConnectionFactory sentinelLettuceConnectionFactory(LettuceClientConfiguration lettuceClientConfiguration) {
		// RedisSentinelConfiguration
		Set<String> sentinelHostPortSet = Arrays.stream(redisSentinelNodes.split(Constant.COMMA)).collect(Collectors.toSet());
		RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration(redisSentinelMaster, sentinelHostPortSet);
		// LettuceConnectionFactory
		LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisSentinelConfiguration, lettuceClientConfiguration);
		lettuceConnectionFactory.afterPropertiesSet();
		log.info("Redis:sentinelLettuceConnectionFactory:初始化完成");
		return lettuceConnectionFactory;
	}

	@Bean
	public RedisTemplate<String, Object> sentinelRedisTemplate(RedisConnectionFactory sentinelLettuceConnectionFactory,
															   RedisSerializer<String> keySerializer,
															   RedisSerializer<Object> valueSerializer) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(sentinelLettuceConnectionFactory);
		redisTemplate.setKeySerializer(keySerializer);
		redisTemplate.setValueSerializer(valueSerializer);
		redisTemplate.setHashKeySerializer(keySerializer);
		redisTemplate.setHashValueSerializer(valueSerializer);
		redisTemplate.afterPropertiesSet();
		log.info("Redis:sentinelRedisTemplate:初始化完成");
		return redisTemplate;
	}

	@Bean
	public LettuceConnectionFactory clusterLettuceConnectionFactory(LettuceClientConfiguration lettuceClientConfiguration) {
		// RedisClusterConfiguration
		Set<String> clusterHostPortSet = Arrays.stream(redisClusterNodes.split(Constant.COMMA)).collect(Collectors.toSet());
		RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(clusterHostPortSet);
		redisClusterConfiguration.setPassword(RedisPassword.of("Hhsrv587.."));// TODO 记得删掉
		// LettuceConnectionFactory
		LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisClusterConfiguration, lettuceClientConfiguration);
		lettuceConnectionFactory.afterPropertiesSet();
		log.info("Redis:clusterLettuceConnectionFactory:初始化完成");
		return lettuceConnectionFactory;
	}

	@Bean
	public RedisTemplate<String, Object> clusterRedisTemplate(RedisConnectionFactory clusterLettuceConnectionFactory,
															  RedisSerializer<String> keySerializer,
															  RedisSerializer<Object> valueSerializer) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(clusterLettuceConnectionFactory);
		redisTemplate.setKeySerializer(keySerializer);
		redisTemplate.setValueSerializer(valueSerializer);
		redisTemplate.setHashKeySerializer(keySerializer);
		redisTemplate.setHashValueSerializer(valueSerializer);
		redisTemplate.afterPropertiesSet();
		log.info("Redis:clusterRedisTemplate:初始化完成");
		return redisTemplate;
	}

}
