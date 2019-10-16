package com.mrathena.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author mrathena on 2019-10-16 23:11
 */
@Slf4j
public class RedisTest extends BaseTest {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Test
	public void test() {
		stringRedisTemplate.opsForValue().set("key", "value", 10L, TimeUnit.SECONDS);
		System.out.println(stringRedisTemplate.opsForValue().get("key"));
		redisTemplate.opsForValue().set("key", "value", 10L, TimeUnit.SECONDS);
		System.out.println(redisTemplate.opsForValue().get("key"));
		System.out.println(stringRedisTemplate.opsForValue().get("key"));
		redisTemplate.opsForValue().set("key", 1, 10L, TimeUnit.SECONDS);
		System.out.println(redisTemplate.opsForValue().get("key"));
		redisTemplate.opsForValue().increment("key");
		System.out.println(stringRedisTemplate.opsForValue().get("key"));
	}

}
