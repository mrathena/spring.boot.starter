package com.mrathena.test;

import com.mrathena.biz.toolkit.Redis;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.redisson.api.RLock;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.List;

/**
 * @author mrathena on 2019-10-16 23:11
 */
@Slf4j
public class RedisTest extends BaseTest {

	@Resource(name = "clusterRedisTemplate")
	private RedisTemplate<String, Object> redisTemplate;

	@Resource
	private Redis redis;

	@Test
	public void testSetMethod() {
//		redis.set("test", 1L, Duration.ofMinutes(1));
//		System.out.println(redis.getString("test"));
		System.out.println(redis.get("test", Long.class));
		System.out.println(redis.get("test", long.class));
		redis.set("test", "value", Duration.ofMinutes(1));
		System.out.println(redis.getString("test"));
		System.out.println(redis.get("test", String.class));
	}

	@Test
	public void testExecute() {
		Object object = redisTemplate.execute((RedisCallback<Object>) connection -> {
			RedisSerializer<?> valueSerializer = redisTemplate.getValueSerializer();
			return valueSerializer.deserialize(connection.get("key".getBytes()));
		});
		System.out.println(object);
	}

	@Test
	public void testExecutePipeline() {
		List<Object> objects = redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
			connection.get("key".getBytes());
			connection.get("key".getBytes());
			connection.get("key".getBytes());
			return null;
		});
		System.out.println(objects);
	}

	@Test
	public void testCacheNodesAndCalculateSlot() {
		// 测试集群模式下, RedisTemplate 是否会缓存 nodes and slot? 执行操作时, 直接计算 slot 位置, 直接连接对应 node, 而不是集群内跳转
		redisTemplate.hasKey("key");
		redisTemplate.hasKey("key");
		redisTemplate.hasKey("key");
		redisTemplate.hasKey("key");
		redisTemplate.hasKey("key");
		redisTemplate.hasKey("key");
		redisTemplate.hasKey("key");
		redisTemplate.hasKey("key");
	}

	@Test
	public void redisTest() {
		String key = "redbag:redis:cluster:test";
		testHasKey(key);
		testSet(key);
		testIncrDecr(key);
		testLock(key);
	}

	public void testHasKey(String key) {
		System.out.println("testHasKey");
		System.out.println(redis.hasKey(key));
		redis.set(key, "this is a test for hasKey", Duration.ofHours(1));
		System.out.println(redis.hasKey(key));
		redis.del(key);
		System.out.println(redis.hasKey(key));
		System.out.println(redis.getString(key));
	}

	public void testSet(String key) {
		System.out.println("testSet");
		redis.set(key, "this is a test for set operation", Duration.ofHours(1));
//		System.out.println(redis.get(key));
//		System.out.println(redis.get(key).getClass());
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//		redis.set(key, new Date(), RedisConstant.HOUR_IN_MILLISECONDS);
		System.out.println(redis.pttl(key));
//		System.out.println(redis.get(key));
//		System.out.println(redis.get(key).getClass());
//		Date date = (Date) redis.get(key);
//		System.out.println(date);
	}

	public void testIncrDecr(String key) {
		System.out.println("testIncrDecr");
		redis.del(key);
		System.out.println(redis.increment(key));
		System.out.println(redis.decrement(key));
		System.out.println(redis.decrement(key));
		System.out.println(redis.increment(key));
		System.out.println(redis.increment(key));
		System.out.println(redis.decrement(key));
		System.out.println(redis.increment(key));
		System.out.println(redis.increment(key));
		System.out.println(redis.decrement(key));
		System.out.println(redis.increment(key));
	}

	public void testLock(String key) {
		System.out.println("testLock");
		redis.del(key);
		RLock lock = redis.getLock(key);
		System.out.println(lock);
		System.out.println(lock.tryLock());
		System.out.println(lock.tryLock());
		lock.unlock();
		System.out.println(lock.tryLock());
		lock.unlock();
		lock.unlock();
		// 可重入锁, 同一个线程可以多次加锁, 但是解锁次数得对应, 解多了会报错
//		lock.unlock();
		// 第二次加锁
		System.out.println(lock.tryLock());
		// 同时再用另一个线程来加锁
		try {
			Thread thread = new Thread(() -> log.info("锁被占用,多线程尝试获取锁,预期False: {}", lock.tryLock()));
			thread.join();
			thread.start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		lock.unlock();
		new Thread(() -> log.info("锁被释放,多线程尝试获取锁,预期True: {}", lock.tryLock())).start();
		redis.del(key);
	}

}
