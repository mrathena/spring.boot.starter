package com.mrathena.web.configuration;

import com.mrathena.common.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mrathena on 2019/11/12 14:48
 */
@Slf4j
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
		// 需要配置集群节点扫描间隔,默认是5秒,可以配个一小时一次
//		config.useClusterServers().addNodeAddress(redisClusterNodeArray).setScanInterval(1000 * 60 * 60).setPassword("Hhsrv587..");// TODO 记得删掉
		config.useClusterServers().addNodeAddress(redisClusterNodeArray).setScanInterval(1000 * 60 * 60);
		log.info("Redisson:RedissonClient:初始化完成");
		return Redisson.create(config);
	}

}
