package com.mrathena;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author mrathena on 2019/7/27 16:00
 */
@EnableDubbo
@EnableCaching
@EnableScheduling
@SpringBootApplication(exclude = {
		RedisAutoConfiguration.class,
		RedisReactiveAutoConfiguration.class,
		RedisRepositoriesAutoConfiguration.class
})
@MapperScan("com.mrathena.dao.mapper")
public class Application extends SpringBootServletInitializer {

	/**
	 * war包部署的话,需要这个配置
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}