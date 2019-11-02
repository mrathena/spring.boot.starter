package com.mrathena.controller;

import com.mrathena.remote.y2019.demo.ProvinceCityServiceRemote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author mrathena on 2019/7/27 16:01
 */
@Slf4j
@RestController
@RequestMapping("test")
public class TestController {

	@Resource
	private JdbcTemplate jdbcTemplate;

	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	@Resource
	private ProvinceCityServiceRemote provinceCityServiceRemote;

	@GetMapping("index")
	public String index() {
		return "Hello World";
	}

	/**
	 * 测试 datasource
	 */
	@RequestMapping("datasource/customer")
	public Object user() {
		String sql = "select * from customer";
		return jdbcTemplate.queryForList(sql);
	}

	/**
	 * 测试 datasource
	 */
	@RequestMapping("datasource/customer/{id}")
	public Object user(@PathVariable Long id) {
		String sql = "select * from customer where id = " + id;
		return jdbcTemplate.queryForMap(sql);
	}

	/**
	 * 测试 dubbo consumer
	 */
	@RequestMapping("dubbo/consumer/province")
	public Object province() {
		return provinceCityServiceRemote.queryProvinceList();
	}

	/**
	 * 测试 dubbo consumer
	 */
	@RequestMapping("dubbo/consumer/city/{provinceCode}")
	public Object province(@PathVariable String provinceCode) {
		return provinceCityServiceRemote.queryCityListByProvinceCode(provinceCode);
	}

	/**
	 * 测试 redis set
	 */
	@RequestMapping("redis/set/{key}/{value}")
	public Object set(@PathVariable String key, @PathVariable String value) {
		redisTemplate.opsForValue().set(key, value, 1000 * 10, TimeUnit.MILLISECONDS);
		return "SUCCESS";
	}

	/**
	 * 测试 redis get
	 */
	@RequestMapping("redis/get/{key}")
	public Object get(@PathVariable String key) {
		return redisTemplate.opsForValue().get(key);
	}

}
