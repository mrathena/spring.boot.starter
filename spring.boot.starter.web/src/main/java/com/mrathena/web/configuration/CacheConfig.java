package com.mrathena.web.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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

	/**
	 * 注解 @Primary, 指定默认使用的bean, 在配置多个相同类型bean的时候使用
	 */
	@Bean
	@Primary
	public CacheManager redisClusterCacheManager(RedisConnectionFactory clusterLettuceConnectionFactory,
												 RedisSerializer<String> keySerializer,
												 RedisSerializer<Object> valueSerializer) {
		// RedisCacheConfiguration commonRedisCacheConfiguration
		RedisCacheConfiguration commonRedisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
				.disableKeyPrefix()
				.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer))
				.disableCachingNullValues();
		// CacheConfigurationsMap
		Map<String, RedisCacheConfiguration> cacheConfigurationMap = new HashMap<>(8);
		cacheConfigurationMap.put(CacheNameEnum.ONE_MINUTE.name(), commonRedisCacheConfiguration.entryTtl(Duration.ofMinutes(1)));
		cacheConfigurationMap.put(CacheNameEnum.FIVE_MINUTE.name(), commonRedisCacheConfiguration.entryTtl(Duration.ofMinutes(5)));
		cacheConfigurationMap.put(CacheNameEnum.TEN_MINUTE.name(), commonRedisCacheConfiguration.entryTtl(Duration.ofMinutes(10)));
		cacheConfigurationMap.put(CacheNameEnum.THIRTY_MINUTE.name(), commonRedisCacheConfiguration.entryTtl(Duration.ofMinutes(30)));
		cacheConfigurationMap.put(CacheNameEnum.ONE_HOUR.name(), commonRedisCacheConfiguration.entryTtl(Duration.ofHours(1)));
		cacheConfigurationMap.put(CacheNameEnum.SIX_HOUR.name(), commonRedisCacheConfiguration.entryTtl(Duration.ofHours(6)));
		cacheConfigurationMap.put(CacheNameEnum.TWELVE_HOUR.name(), commonRedisCacheConfiguration.entryTtl(Duration.ofHours(12)));
		cacheConfigurationMap.put(CacheNameEnum.ONE_DAY.name(), commonRedisCacheConfiguration.entryTtl(Duration.ofDays(1)));
		cacheConfigurationMap.put(CacheNameEnum.SEVEN_DAY.name(), commonRedisCacheConfiguration.entryTtl(Duration.ofDays(7)));
		cacheConfigurationMap.put(CacheNameEnum.FOURTEEN_DAY.name(), commonRedisCacheConfiguration.entryTtl(Duration.ofDays(14)));
		cacheConfigurationMap.put(CacheNameEnum.ONE_MONTH.name(), commonRedisCacheConfiguration.entryTtl(Duration.ofDays(31)));
		cacheConfigurationMap.put(CacheNameEnum.THREE_MONTH.name(), commonRedisCacheConfiguration.entryTtl(Duration.ofDays(93)));
		cacheConfigurationMap.put(CacheNameEnum.SIX_MONTH.name(), commonRedisCacheConfiguration.entryTtl(Duration.ofDays(182)));
		cacheConfigurationMap.put(CacheNameEnum.ONE_YEAR.name(), commonRedisCacheConfiguration.entryTtl(Duration.ofDays(366)));
		cacheConfigurationMap.put(CacheNameEnum.FOREVER.name(), commonRedisCacheConfiguration.entryTtl(Duration.ZERO));
		// RedisCacheManager
		RedisCacheManager redisCacheManager = RedisCacheManager.builder(clusterLettuceConnectionFactory)
				.withInitialCacheConfigurations(cacheConfigurationMap)
				.transactionAware()
				// 不允许添加除上述定义之外的缓存名称
				.disableCreateOnMissingCache()
				.build();
		log.info("Cache:redisClusterCacheManager:初始化完成");
		return redisCacheManager;
	}

	public enum CacheNameEnum {
		/**
		 * 缓存时间
		 */
		ONE_MINUTE, FIVE_MINUTE, TEN_MINUTE, THIRTY_MINUTE,
		ONE_HOUR, SIX_HOUR, TWELVE_HOUR,
		ONE_DAY, SEVEN_DAY, FOURTEEN_DAY,
		ONE_MONTH, THREE_MONTH, SIX_MONTH,
		ONE_YEAR,
		FOREVER
	}

}
