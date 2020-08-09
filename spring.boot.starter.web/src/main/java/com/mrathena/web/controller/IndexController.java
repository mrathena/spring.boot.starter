package com.mrathena.web.controller;

import com.mrathena.spring.boot.starter.api.business.y2020.index.IndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("index")
public class IndexController {

	@Resource
	private IndexService indexService;

	@GetMapping("log")
	public void log() {
		indexService.log();
	}

	@GetMapping("async")
	public void async() {
		indexService.async();
	}

	@GetMapping("sync")
	public void sync() {
		indexService.sync();
	}

}
