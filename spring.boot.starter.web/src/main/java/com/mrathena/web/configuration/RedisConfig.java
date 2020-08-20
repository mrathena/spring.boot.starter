package com.mrathena.web.configuration;

import com.mrathena.common.constant.Constant;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.ReadFrom;
import io.lettuce.core.SocketOptions;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
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

	@Value("${spring.redis.cluster.nodes}")
	private String clusterNodes;
	@Value("${spring.redis.sentinel.master}")
	private String sentinelMaster;
	@Value("${spring.redis.sentinel.nodes}")
	private String sentinelNodes;

	@Bean
	public RedisSerializer<String> keySerializer() {
		return StringRedisSerializer.UTF_8;
	}

	@Bean
	public RedisSerializer<Object> valueSerializer() {
		return new GenericJackson2JsonRedisSerializer();
	}

	@Bean
	public LettuceConnectionFactory clusterLettuceConnectionFactory() {
		// RedisClusterConfiguration
		Set<String> clusterSet = Arrays.stream(clusterNodes.split(Constant.COMMA)).collect(Collectors.toSet());
		RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(clusterSet);
		redisClusterConfiguration.setMaxRedirects(5);
		// ClusterTopologyRefreshOptions - Options to control the Cluster topology refreshing of {@link RedisClusterClient}.
		// 开启自适应刷新和定时刷新(定时刷新我感觉没必要). 如自适应刷新不开启, Redis集群拓扑结构变更时将会导致连接异常
		ClusterTopologyRefreshOptions clusterTopologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
				// 开启自适应刷新
				// .enableAdaptiveRefreshTrigger(ClusterTopologyRefreshOptions.RefreshTrigger.MOVED_REDIRECT, ClusterTopologyRefreshOptions.RefreshTrigger.PERSISTENT_RECONNECTS)
				// 开启所有自适应刷新, MOVED_REDIRECT,ASK_REDIRECT,PERSISTENT_RECONNECTS,UNCOVERED_SLOT,UNKNOWN_NODE 都会触发
				// 本地提前缓存好了节点与插槽的印射关系,执行命令时先计算出key对应的插槽,即可知道存储该key的节点,直接向对应节点发送命令,避免了redis集群做moved操作,可提升效率
				// 但是如果集群拓扑结构发生了变化(如新增了节点),本地缓存的节点与插槽的关系会不准确,命令执行时可能发生moved,这时候就会触发拓扑结构刷新操作
				.enableAllAdaptiveRefreshTriggers()
				// 自适应刷新超时时间(默认30秒)
				// .adaptiveRefreshTriggersTimeout(Duration.ofSeconds(30))
				// 开启周期刷新, 默认60秒
				// .enablePeriodicRefresh()
				// .enablePeriodicRefresh(Duration.ofHours(1))
				.build();
		// SocketOptions - Options to configure low-level socket options for the connections kept to Redis servers.
		SocketOptions socketOptions = SocketOptions.builder()
				.keepAlive(true)
				.tcpNoDelay(true)
				.build();
		// ClusterClientOptions - Client Options to control the behavior of {@link RedisClusterClient}.
		ClusterClientOptions clusterClientOptions = ClusterClientOptions.builder()
				.topologyRefreshOptions(clusterTopologyRefreshOptions)
				.socketOptions(socketOptions)
				// 默认就是重连的
				// .autoReconnect()
				// .maxRedirects(5)
				// Accept commands when auto-reconnect is enabled, reject commands when auto-reconnect is disabled
				.disconnectedBehavior(ClientOptions.DisconnectedBehavior.DEFAULT)
				// 取消校验集群节点的成员关系, 默认是true, 需要校验
				.validateClusterNodeMembership(false)
				.build();
		// LettucePoolingClientConfiguration - Redis client configuration for lettuce using a driver level pooled connection by adding pooling specific configuration to {@link LettuceClientConfiguration}
		LettucePoolingClientConfiguration lettucePoolingClientConfiguration = LettucePoolingClientConfiguration.builder()
				.poolConfig(getGenericObjectPoolConfig())
				.clientOptions(clusterClientOptions)
				.readFrom(ReadFrom.REPLICA_PREFERRED)
				.commandTimeout(Duration.ofMillis(100))
				.build();
		// LettuceConnectionFactory
		return new LettuceConnectionFactory(redisClusterConfiguration, lettucePoolingClientConfiguration);
	}

	@Bean
	public LettuceConnectionFactory sentinelLettuceConnectionFactory() {
		// RedisSentinelConfiguration
		Set<String> sentinelSet = Arrays.stream(sentinelNodes.split(Constant.COMMA)).collect(Collectors.toSet());
		RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration(sentinelMaster, sentinelSet);
		// LettucePoolingClientConfiguration
		LettucePoolingClientConfiguration lettucePoolingClientConfiguration = LettucePoolingClientConfiguration.builder()
				.poolConfig(getGenericObjectPoolConfig())
				.commandTimeout(Duration.ofMillis(100))
				.build();
		// LettuceConnectionFactory
		return new LettuceConnectionFactory(redisSentinelConfiguration, lettucePoolingClientConfiguration);
	}

	private GenericObjectPoolConfig<?> getGenericObjectPoolConfig() {
		GenericObjectPoolConfig<?> genericObjectPoolConfig = new GenericObjectPoolConfig<>();
		genericObjectPoolConfig.setMaxTotal(8);
		genericObjectPoolConfig.setMaxIdle(8);
		genericObjectPoolConfig.setMinIdle(0);
		genericObjectPoolConfig.setMaxWaitMillis(1000);
		genericObjectPoolConfig.setTestOnCreate(true);
		genericObjectPoolConfig.setTestOnBorrow(false);
		genericObjectPoolConfig.setTestOnReturn(false);
		genericObjectPoolConfig.setTestWhileIdle(true);
		genericObjectPoolConfig.setBlockWhenExhausted(false);
		return genericObjectPoolConfig;
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory clusterLettuceConnectionFactory,
	                                                   RedisSerializer<String> keySerializer,
	                                                   RedisSerializer<Object> valueSerializer) {
		return generateRedisTemplate(clusterLettuceConnectionFactory, keySerializer, valueSerializer);
	}

	@Bean("sentinelRedisTemplate")
	public RedisTemplate<String, Object> sentinelRedisTemplate(LettuceConnectionFactory sentinelLettuceConnectionFactory,
	                                                           RedisSerializer<String> keySerializer,
	                                                           RedisSerializer<Object> valueSerializer) {
		return generateRedisTemplate(sentinelLettuceConnectionFactory, keySerializer, valueSerializer);
	}

	private RedisTemplate<String, Object> generateRedisTemplate(LettuceConnectionFactory lettuceConnectionFactory,
	                                                            RedisSerializer<String> keySerializer,
	                                                            RedisSerializer<Object> valueSerializer) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(lettuceConnectionFactory);
		redisTemplate.setKeySerializer(keySerializer);
		redisTemplate.setValueSerializer(valueSerializer);
		redisTemplate.setHashKeySerializer(keySerializer);
		redisTemplate.setHashValueSerializer(valueSerializer);
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}

	@Bean
	public RedissonClient redissonClient() {
		String[] redisClusterNodeArray = clusterNodes.split(Constant.COMMA);
		for (int i = 0; i < redisClusterNodeArray.length; i++) {
			redisClusterNodeArray[i] = "redis://".concat(redisClusterNodeArray[i]);
		}
		Config config = new Config();
		config.useClusterServers().addNodeAddress(redisClusterNodeArray).setScanInterval(1000 * 60 * 60);
		return Redisson.create(config);
	}

}
