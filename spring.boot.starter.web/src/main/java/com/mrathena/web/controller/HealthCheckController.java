package com.mrathena.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedInputStream;

/**
 * @author mrathena on 2019/11/6 19:56
 */
@Slf4j
@RestController
public class HealthCheckController {

	private String response;

	@RequestMapping("healthcheck.html")
	public String healthCheck() {
		try (BufferedInputStream bis = new BufferedInputStream(HealthCheckController.class.getResourceAsStream("/healthcheck.html"))) {
			StringBuilder sb = new StringBuilder();
			byte[] line = new byte[2048];
			while (bis.read(line) != -1) {
				sb.append(new String(line));
			}
			response = sb.toString().trim();
		} catch (Exception e) {
			log.error(null, e);
		}
		return response;
	}

}
