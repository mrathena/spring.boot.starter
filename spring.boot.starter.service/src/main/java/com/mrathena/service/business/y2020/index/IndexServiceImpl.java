package com.mrathena.service.business.y2020.index;

import com.mrathena.spring.boot.starter.api.business.y2020.index.IndexService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class IndexServiceImpl implements IndexService {

	@Resource
	private ThreadPoolTaskExecutor taskExecutor;

	@Override
	public void log() {
		log.trace("trace");
		log.debug("debug");
		log.info("info");
		log.warn("warn");
		log.error("error");
	}

	@Override
	public void async() {
		try {
			CountDownLatch countDownLatch = new CountDownLatch(1000000);
			for (int i = 0; i < 1000000; i++) {
				int index = i;
				taskExecutor.execute(() -> {
					try {
						log.error("this is a logback async test, {}", index);
					} finally {
						countDownLatch.countDown();
					}
				});
			}
			countDownLatch.await(1, TimeUnit.HOURS);
		} catch (Throwable cause) {
			log.error("", cause);
		}
	}

	@Override
	public void sync() {
		try {
			CountDownLatch countDownLatch = new CountDownLatch(1000000);
			for (int i = 0; i < 1000000; i++) {
				int index = i;
				taskExecutor.execute(() -> {
					try {
						log.warn("this is a logback sync test, {}", index);
					} finally {
						countDownLatch.countDown();
					}
				});
			}
			countDownLatch.await(1, TimeUnit.HOURS);
		} catch (Throwable cause) {
			log.error("", cause);
		}
	}

}
