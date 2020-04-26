package com.mrathena.web.configuration;

import com.mrathena.common.constant.Constant;
import io.lettuce.core.resource.ClientResources;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
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

	/**
	 * 创建多个RedisClient实例的话,需要配置公用的ClientResources,不然就会报
	 * LEAK: HashedWheelTimer.release() was not called before it's garbage-collected.
	 */
	@Bean(destroyMethod = "shutdown")
	ClientResources clientResources() {
		return ClientResources.create();
	}

	@Bean
	public LettuceClientConfiguration lettuceClientConfiguration(ClientResources clientResources) {
		// GenericObjectPoolConfig
		GenericObjectPoolConfig<?> genericObjectPoolConfig = new GenericObjectPoolConfig<>();
		// TODO 这些值不知道该写多少
		genericObjectPoolConfig.setMaxTotal(10);
		genericObjectPoolConfig.setMaxIdle(10);
		genericObjectPoolConfig.setMinIdle(10);
		genericObjectPoolConfig.setTestOnCreate(true);
		genericObjectPoolConfig.setTestOnBorrow(false);
		genericObjectPoolConfig.setTestOnReturn(false);
		genericObjectPoolConfig.setTestWhileIdle(true);
		// LettucePoolingClientConfiguration
		LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder builder = LettucePoolingClientConfiguration.builder();
		builder.poolConfig(genericObjectPoolConfig).commandTimeout(Duration.ofMillis(2000L)).clientResources(clientResources);
		LettucePoolingClientConfiguration lettucePoolingClientConfiguration = builder.build();
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
//		redisClusterConfiguration.setPassword(RedisPassword.of("Hhsrv587.."));// TODO 记得删掉
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
