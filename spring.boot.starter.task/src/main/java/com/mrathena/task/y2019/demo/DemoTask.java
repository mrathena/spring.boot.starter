package com.mrathena.task.y2019.demo;

import com.mrathena.common.toolkit.IdKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author mrathena on 2019/11/2 18:30
 */
@Slf4j
@Component
public class DemoTask {

	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	@Scheduled(cron = "0/10 * * * * ?")
	public void task() {
		System.out.println("Demo Scheduled Task pre 10 seconds");
	}

	@Scheduled(cron = "0/1 * * * * ?")
	public void redisDemoKeyTask() {
		redisTemplate.opsForValue().set("DEMO", IdKit.getSerialNo(), 1, TimeUnit.SECONDS);
	}

}
