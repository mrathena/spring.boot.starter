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
		// RedisCacheConfiguration daily weekly monthly yearly eternally
		RedisCacheConfiguration oneMinuteRedisCacheConfiguration = commonRedisCacheConfiguration.entryTtl(Duration.ofMinutes(1));
		RedisCacheConfiguration fiveMinuteRedisCacheConfiguration = commonRedisCacheConfiguration.entryTtl(Duration.ofMinutes(5));
		RedisCacheConfiguration tenMinuteRedisCacheConfiguration = commonRedisCacheConfiguration.entryTtl(Duration.ofMinutes(10));
		RedisCacheConfiguration thirtyMinuteRedisCacheConfiguration = commonRedisCacheConfiguration.entryTtl(Duration.ofMinutes(30));
		RedisCacheConfiguration oneHourRedisCacheConfiguration = commonRedisCacheConfiguration.entryTtl(Duration.ofHours(1));
		RedisCacheConfiguration sixHourRedisCacheConfiguration = commonRedisCacheConfiguration.entryTtl(Duration.ofHours(6));
		RedisCacheConfiguration twelveHourRedisCacheConfiguration = commonRedisCacheConfiguration.entryTtl(Duration.ofHours(12));
		RedisCacheConfiguration oneDayRedisCacheConfiguration = commonRedisCacheConfiguration.entryTtl(Duration.ofDays(1));
		RedisCacheConfiguration sevenDayRedisCacheConfiguration = commonRedisCacheConfiguration.entryTtl(Duration.ofDays(7));
		RedisCacheConfiguration fourteenDayRedisCacheConfiguration = commonRedisCacheConfiguration.entryTtl(Duration.ofDays(7));
		RedisCacheConfiguration oneMonthRedisCacheConfiguration = commonRedisCacheConfiguration.entryTtl(Duration.ofDays(31));
		RedisCacheConfiguration threeMonthRedisCacheConfiguration = commonRedisCacheConfiguration.entryTtl(Duration.ofDays(93));
		RedisCacheConfiguration sixMonthRedisCacheConfiguration = commonRedisCacheConfiguration.entryTtl(Duration.ofDays(182));
		RedisCacheConfiguration oneYearRedisCacheConfiguration = commonRedisCacheConfiguration.entryTtl(Duration.ofDays(366));
		// Duration.ZERO 存入redis的时候是永久
		RedisCacheConfiguration foreverRedisCacheConfiguration = commonRedisCacheConfiguration.entryTtl(Duration.ZERO);
		// CacheConfigurationsMap
		Map<String, RedisCacheConfiguration> cacheConfigurationMap = new HashMap<>(8);
		cacheConfigurationMap.put(CacheNameEnum.ONE_MINUTE.name(), oneMinuteRedisCacheConfiguration);
		cacheConfigurationMap.put(CacheNameEnum.FIVE_MINUTE.name(), fiveMinuteRedisCacheConfiguration);
		cacheConfigurationMap.put(CacheNameEnum.TEN_MINUTE.name(), tenMinuteRedisCacheConfiguration);
		cacheConfigurationMap.put(CacheNameEnum.THIRTY_MINUTE.name(), thirtyMinuteRedisCacheConfiguration);
		cacheConfigurationMap.put(CacheNameEnum.ONE_HOUR.name(), oneHourRedisCacheConfiguration);
		cacheConfigurationMap.put(CacheNameEnum.SIX_HOUR.name(), sixHourRedisCacheConfiguration);
		cacheConfigurationMap.put(CacheNameEnum.TWELVE_HOUR.name(), twelveHourRedisCacheConfiguration);
		cacheConfigurationMap.put(CacheNameEnum.ONE_DAY.name(), oneDayRedisCacheConfiguration);
		cacheConfigurationMap.put(CacheNameEnum.SEVEN_DAY.name(), sevenDayRedisCacheConfiguration);
		cacheConfigurationMap.put(CacheNameEnum.FOURTEEN_DAY.name(), fourteenDayRedisCacheConfiguration);
		cacheConfigurationMap.put(CacheNameEnum.ONE_MONTH.name(), oneMonthRedisCacheConfiguration);
		cacheConfigurationMap.put(CacheNameEnum.THREE_MONTH.name(), threeMonthRedisCacheConfiguration);
		cacheConfigurationMap.put(CacheNameEnum.SIX_MONTH.name(), sixMonthRedisCacheConfiguration);
		cacheConfigurationMap.put(CacheNameEnum.ONE_YEAR.name(), oneYearRedisCacheConfiguration);
		cacheConfigurationMap.put(CacheNameEnum.FOREVER.name(), foreverRedisCacheConfiguration);
		// RedisCacheManager
		RedisCacheManager redisCacheManager = RedisCacheManager.builder(clusterLettuceConnectionFactory)
				.cacheDefaults(oneDayRedisCacheConfiguration)
				.withInitialCacheConfigurations(cacheConfigurationMap)
				.transactionAware()
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
