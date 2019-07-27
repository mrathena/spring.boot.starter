package com.mrathena.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author mrathena on 2019/7/27 16:01
 */
@Slf4j
@RestController
@RequestMapping("test")
public class TestController {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@GetMapping("index")
	public String index() {
		return "Hello World";
	}

	@RequestMapping("customer/{id}")
	public Object user(@PathVariable Long id) {
		log.info("CustomerId:{}", id);
		String sql = "select * from t_customer where id = " + id;
		Map<String, Object> result = jdbcTemplate.queryForMap(sql);
		log.info("Result:{}", result);
		return result;
	}

}
