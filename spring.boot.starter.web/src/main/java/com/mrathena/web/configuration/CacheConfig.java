package com.mrathena.web.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mrathena on 2019/12/10 15:45
 */
@Slf4j
@Configuration
public class CacheConfig {

	@Bean
	public CacheManager commonRedisClusterCacheManager(RedisConnectionFactory clusterLettuceConnectionFactory,
													   RedisSerializer<String> keySerializer,
													   RedisSerializer<Object> valueSerializer) {
		// RedisCacheConfiguration
		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofDays(1))
				.disableKeyPrefix()
				.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer))
				.disableCachingNullValues();
		// RedisCacheManager
		RedisCacheManager redisCacheManager = RedisCacheManager.builder(clusterLettuceConnectionFactory)
				.cacheDefaults(redisCacheConfiguration)
				.transactionAware()
				.build();
		log.info("Cache:commonRedisClusterCacheManager:初始化完成");
		return redisCacheManager;
	}

	@Bean
	public CacheManager redisClusterCacheManager(RedisConnectionFactory clusterLettuceConnectionFactory,
												 RedisSerializer<String> keySerializer,
												 RedisSerializer<Object> valueSerializer) {
		// RedisCacheConfiguration commonRedisCacheConfiguration
		RedisCacheConfiguration commonRedisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
				.disableKeyPrefix()
				.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer))
				.disableCachingNullValues();
		// RedisCacheConfiguration daily weekly monthly yearly eternally
		RedisCacheConfiguration dailyRedisCacheConfiguration = commonRedisCacheConfiguration.entryTtl(Duration.ofDays(1));
		RedisCacheConfiguration weeklyRedisCacheConfiguration = commonRedisCacheConfiguration.entryTtl(Duration.ofDays(7));
		RedisCacheConfiguration monthlyRedisCacheConfiguration = commonRedisCacheConfiguration.entryTtl(Duration.ofDays(31));
		RedisCacheConfiguration yearlyRedisCacheConfiguration = commonRedisCacheConfiguration.entryTtl(Duration.ofDays(366));
		// TODO Duration.ZERO 会是永久吗
		RedisCacheConfiguration eternallyRedisCacheConfiguration = commonRedisCacheConfiguration.entryTtl(Duration.ZERO);
		// CacheConfigurationsMap
		Map<String, RedisCacheConfiguration> cacheConfigurationMap = new HashMap<>(8);
		cacheConfigurationMap.put(CacheNameEnum.DAILY.name(), dailyRedisCacheConfiguration);
		cacheConfigurationMap.put(CacheNameEnum.WEEKLY.name(), weeklyRedisCacheConfiguration);
		cacheConfigurationMap.put(CacheNameEnum.MONTHLY.name(), monthlyRedisCacheConfiguration);
		cacheConfigurationMap.put(CacheNameEnum.YEARLY.name(), yearlyRedisCacheConfiguration);
		cacheConfigurationMap.put(CacheNameEnum.ETERNALLY.name(), eternallyRedisCacheConfiguration);
		// RedisCacheManager
		RedisCacheManager redisCacheManager = RedisCacheManager.builder(clusterLettuceConnectionFactory)
				.cacheDefaults(dailyRedisCacheConfiguration)
				.withInitialCacheConfigurations(cacheConfigurationMap)
				.transactionAware()
				.build();
		log.info("Cache:redisClusterCacheManager:初始化完成");
		return redisCacheManager;
	}

	public enum CacheNameEnum {
		/**
		 * 日,周,月,年,永久
		 */
		DAILY, WEEKLY, MONTHLY, YEARLY, ETERNALLY
	}

}
