package com.mrathena.biz.y2019.demo;

import com.mrathena.common.toolkit.IdKit;
import com.mrathena.spring.boot.starter.api.y2019.demo.DemoDto;
import com.mrathena.spring.boot.starter.api.y2019.demo.DemoReqDto;
import com.mrathena.spring.boot.starter.api.y2019.demo.DemoResDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author mrathena on 2019/10/21 23:01
 */
@Slf4j
@Component
public class DemoBiz {

	public DemoResDto demoMethod(DemoReqDto request) {
		DemoResDto demoResDto = new DemoResDto();
		demoResDto.setDemoStringFiled(IdKit.getSerialNo());
		DemoDto demoDto = new DemoDto();
		demoDto.setId(new Random().nextLong());
		demoDto.setUsername(IdKit.getUuid());
		demoDto.setAge(new Random().nextInt());
		demoDto.setMale(new Random().nextBoolean());
		List<DemoDto> list = new LinkedList<>();
		list.add(demoDto);
		demoResDto.setDemoObjectListFiled(list);
		return demoResDto;
	}

}
