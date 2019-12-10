package com.mrathena.biz.toolkit;

import com.mrathena.common.exception.ExceptionCodeEnum;
import com.mrathena.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * 暂时只是redis.string的操作
 * 如有必要, 再考虑添加其他数据类型的操作
 *
 * @author mrathena on 2019/11/12 15:01
 */
@Slf4j
@Component
public class Redis {

	@Resource
	private RedisTemplate<String, Object> clusterRedisTemplate;
	@Resource
	private RedissonClient redissonClient;

	/**
	 * hasKey
	 */
	public Boolean hasKey(String key) {
		return clusterRedisTemplate.hasKey(key);
	}

	/**
	 * set
	 *
	 * @param milliseconds 过期时间(毫秒)
	 */
	public void set(String key, Object value, long milliseconds) {
		clusterRedisTemplate.opsForValue().set(key, value, milliseconds, TimeUnit.MILLISECONDS);
	}

	/**
	 * set
	 *
	 * @param duration 持续时间, Duration.ofMinutes(1); Duration.ofDays(15);
	 */
	public void set(String key, Object value, Duration duration) {
		clusterRedisTemplate.opsForValue().set(key, value, duration);
	}

	/**
	 * set, 该方法不能保证一定成功,expireAt要确认好
	 *
	 * @param expireAt 未来某个时间点, key将在这个时间点过期
	 */
	public void set(String key, Object value, LocalDateTime expireAt) {
		LocalDateTime now = LocalDateTime.now();
		if (expireAt.equals(now) || expireAt.isBefore(now)) {
			throw new ServiceException(ExceptionCodeEnum.EXCEPTION.getMessage(), "redis在set的时候传入的过期时间点不在当前时间点之后");
		}
		clusterRedisTemplate.opsForValue().set(key, value, Duration.between(now, expireAt).abs());
	}

	/**
	 * del
	 */
	public void del(String key) {
		clusterRedisTemplate.delete(key);
	}

	/**
	 * get, 结果直接强转就可以了
	 * String value = (String) redis.get("key");
	 * List<String> values = (List<String>) redis.get("key");
	 */
	public Object get(String key) {
		return clusterRedisTemplate.opsForValue().get(key);
	}

	/**
	 * get, 返回值直接转换成String
	 */
	public String getString(String key) {
		return (String) clusterRedisTemplate.opsForValue().get(key);
	}

	/**
	 * pttl, 获取key的剩余过期时间(毫秒)
	 */
	public Long pttl(String key) {
		return clusterRedisTemplate.getExpire(key, TimeUnit.MILLISECONDS);
	}

	/**
	 * increment
	 */
	public Long increment(String key) {
		return clusterRedisTemplate.opsForValue().increment(key);
	}

	/**
	 * decrement
	 */
	public Long decrement(String key) {
		return clusterRedisTemplate.opsForValue().decrement(key);
	}

	/**
	 * Redisson.getLock
	 * 该方法只是获取了一个RLock实例,并没有去尝试加锁操作
	 * RLock lock = redissonClient.getLock("key");
	 * 加锁:lock.tryLock(); 返回boolean, true:获取锁成功,false:获取锁失败(锁冲突), 同一线程可以多次加锁(但不要这么做)
	 * 解锁:lock.unLock();
	 */
	public RLock getLock(String key) {
		return redissonClient.getLock(key);
	}

}
