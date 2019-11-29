package com.mrathena.web.controller;

import com.mrathena.common.toolkit.IdKit;
import com.mrathena.remote.base.ProvinceCityServiceRemote;
import com.mrathena.spring.boot.starter.api.business.y2019.demo.CreateDemoReqDTO;
import com.mrathena.spring.boot.starter.api.business.y2019.demo.DemoParentDTO;
import com.mrathena.spring.boot.starter.api.business.y2019.demo.DemoService;
import com.mrathena.spring.boot.starter.api.business.y2019.demo.QueryDemoReqDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author mrathena on 2019/5/8 16:48
 */
@Slf4j
@RestController
@RequestMapping("demo")
public class DemoController {

	@Resource
	private JdbcTemplate jdbcTemplate;

	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	@Resource
	private ProvinceCityServiceRemote provinceCityServiceRemote;

	@Resource
	private DemoService demoService;

	@GetMapping("test")
	public Object test() {
		return "hello world";
	}

	/**
	 * 测试 redis get
	 */
	@RequestMapping("redis/get/{key}")
	public Object redisGet(@PathVariable String key) {
		log.info("{}", key);
		Object value = redisTemplate.opsForValue().get(key);
		log.info("{}", value);
		return value;
	}

	/**
	 * 测试 datasource
	 */
	@RequestMapping("datasource/query/demo")
	public Object datasourceQuery() {
		String sql = "select * from demo";
		return jdbcTemplate.queryForList(sql);
	}

	/**
	 * 测试 datasource
	 */
	@RequestMapping("datasource/query/demo/{id}")
	public Object datasourceQuery(@PathVariable Long id) {
		String sql = "select * from demo where id = " + id;
		return jdbcTemplate.queryForMap(sql);
	}

	/**
	 * 测试 service
	 */
	@RequestMapping("service/create")
	public Object serviceCreate() {
		CreateDemoReqDTO request = new CreateDemoReqDTO();
		request.setTraceNo(IdKit.getUuid());
		request.setChannel("APP");
		request.setChannel2("APP");
		request.setDemo(IdKit.getSerialNo());
		request.setToken("token");
		request.setParent(new DemoParentDTO("father", "mather"));
		request.setProductNo("18234089811");
		return demoService.createDemo(request);
	}

	/**
	 * 测试 service
	 */
	@GetMapping("service/query")
	public Object serviceQuery() {
		Map<String, Object> map = new LinkedHashMap<>();
		QueryDemoReqDTO request = new QueryDemoReqDTO();
		request.setId(1L);
		map.put("queryDemo", demoService.queryDemo(request));
		request.setPageSize(10);
		request.setPageNo(1);
		map.put("queryDemoWithPage", demoService.queryDemoWithPage(request));
		return map;
	}

	/**
	 * 测试 redis set
	 */
	@GetMapping("redis/set/{key}/{value}")
	public Object redisSet(@PathVariable String key, @PathVariable String value) {
		log.info("{}:{}", key, value);
		redisTemplate.opsForValue().set(key, value, 1000 * 10, TimeUnit.MILLISECONDS);
		return "SUCCESS";
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


}
