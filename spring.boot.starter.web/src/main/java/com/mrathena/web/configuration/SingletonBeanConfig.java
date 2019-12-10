package com.mrathena.web.configuration;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mrathena on 2019/11/14 10:41
 */
@Configuration
public class SingletonBeanConfig {

	@Bean
	public Gson gson() {
		return new Gson();
	}

}
