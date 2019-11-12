package com.mrathena.web.configuration;

import com.mrathena.common.constant.Constant;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mrathena on 2019/11/12 14:48
 */
@Configuration
public class RedissonConfig {

	@Value("${spring.redis.cluster.nodes}")
	private String redisClusterNodes;

	/**
	 * redisson, 预计仅用于redis分布式锁, 其他操作都通过redisTemplate和redisCacheManager来完成
	 */
	@Bean
	public RedissonClient redissonClient() {
		String[] redisClusterNodeArray = redisClusterNodes.split(Constant.COMMA);
		for (int i = 0; i < redisClusterNodeArray.length; i++) {
			redisClusterNodeArray[i] = "redis://".concat(redisClusterNodeArray[i]);
		}
		Config config = new Config();
		config.useClusterServers().addNodeAddress(redisClusterNodeArray);
		return Redisson.create(config);
	}

}
