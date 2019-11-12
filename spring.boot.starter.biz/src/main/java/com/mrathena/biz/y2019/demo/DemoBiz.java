package com.mrathena.biz.y2019.demo;

import com.github.pagehelper.PageInfo;
import com.mrathena.common.exception.ServiceException;
import com.mrathena.common.toolkit.ParameterKit;
import com.mrathena.dao.entity.y2019.demo.DemoDO;
import com.mrathena.dao.manager.y2019.demo.DemoManager;
import com.mrathena.spring.boot.starter.api.BasePageResDto;
import com.mrathena.spring.boot.starter.api.y2019.demo.CreateDemoReqDto;
import com.mrathena.spring.boot.starter.api.y2019.demo.DemoDto;
import com.mrathena.spring.boot.starter.api.y2019.demo.QueryDemoReqDto;
import com.mrathena.spring.boot.starter.api.y2019.demo.QueryDemoResDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author mrathena on 2019/11/7 15:49
 */
@Slf4j
@Component
public class DemoBiz {

	@Resource
	private DemoManager demoManager;
	@Resource
	private DemoConverter demoConverter;

	public boolean createDemo(CreateDemoReqDto request) {
		DemoDO demoDO = new DemoDO();
		demoDO.setDemo(request.getDemo());
		return demoManager.create(demoDO);
	}

	public QueryDemoResDto queryDemo(QueryDemoReqDto request) {
		DemoDO demoDO = demoManager.queryDemoById(request.getId());
		if (ObjectUtils.isEmpty(demoDO)) {
			throw new ServiceException("Demo不存在", "没有查到数据");
		}
		QueryDemoResDto result = new QueryDemoResDto();
		result.setDemo(demoDO.getDemo());
		return result;
	}

	public BasePageResDto<DemoDto> queryDemoWithPage(QueryDemoReqDto request) throws Exception {
		PageInfo<DemoDO> page = demoManager.queryDemoListByMapWithPage(ParameterKit.beanToMap(request), request.getPageSize(), request.getPageNo());
		return new BasePageResDto<>(page.getTotal(), demoConverter.to(page.getList()));
	}

}
