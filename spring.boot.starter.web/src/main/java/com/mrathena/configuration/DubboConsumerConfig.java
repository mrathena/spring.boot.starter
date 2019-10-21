package com.mrathena.configuration;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author mrathena on 2019/10/22 0:05
 */
@Configuration
@EnableDubbo(scanBasePackages = "com.mrathena.remote.y2019.demo")
@PropertySource("classpath:spring.dubbo.consumer.yml")
public class DubboConsumerConfig {}