package com.mrathena.configuration;

import org.apache.dubbo.config.ProviderConfig;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author mrathena on 2019/10/21 23:49
 */
@Configuration
@EnableDubbo(scanBasePackages = "com.mrathena.service")
@PropertySource("classpath:spring.dubbo.provider.yml")
public class DubboProviderConfig {

	@Bean
	public ProviderConfig providerConfig() {
		ProviderConfig providerConfig = new ProviderConfig();
		providerConfig.setTimeout(1000);
		return providerConfig;
	}

}