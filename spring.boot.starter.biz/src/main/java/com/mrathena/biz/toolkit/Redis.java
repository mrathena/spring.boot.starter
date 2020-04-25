package com.mrathena.biz.toolkit;

import com.mrathena.common.exception.ErrorCodeEnum;
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
	 * 当前使用spring-data-redis:2.2.1:没有这种问题,不需要特别处理
	 */
	public void set(String key, Object value, long timeout, TimeUnit unit) {
		clusterRedisTemplate.opsForValue().set(key, value, timeout, unit);
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
			throw new ServiceException(ErrorCodeEnum.DATA_EXCEPTION, "redis在set的时候传入的过期时间点不在当前时间点之后");
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
	 * get
	 *
	 * 通过Json转换后,基本数据类型会被反序列化为默认的类型(Integer和Float),
	 * 存Byte,Short,Long取出来是Integer,存Double取出来是Float,可能还有其他的,
	 * 需要做特殊处理(我甚至怀疑使用JdkSerializationRedisSerializer会更好)
	 *
	 * String value = (String) redis.get("key");
	 * List<String> values = (List<String>) redis.get("key");
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(String key, Class<T> clazz) {
		Object object = clusterRedisTemplate.opsForValue().get(key);
		if ((clazz == Long.class || clazz == long.class) && object instanceof Integer) {
			Integer integerObject = (Integer) object;
			return (T) Long.valueOf(integerObject.longValue());
		} else if ((clazz == Short.class || clazz == short.class) && object instanceof Integer) {
			Integer integerObject = (Integer) object;
			return (T) Short.valueOf(integerObject.shortValue());
		} else if ((clazz == Byte.class || clazz == byte.class) && object instanceof Integer) {
			Integer integerObject = (Integer) object;
			return (T) Byte.valueOf(integerObject.byteValue());
		} else if ((clazz == Float.class || clazz == float.class) && object instanceof Double) {
			Double doubleObject = (Double) object;
			return (T) Float.valueOf(doubleObject.floatValue());
		} else if ((clazz == Character.class || clazz == char.class) && object instanceof String) {
			String StringObject = (String) object;
			return (T) Character.valueOf(StringObject.charAt(0));
		} else {
			return (T) object;
		}
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
