package com.mrathena.biz.business.y2019.demo;

import com.github.pagehelper.PageInfo;
import com.mrathena.common.exception.ServiceException;
import com.mrathena.dao.entity.y2019.demo.DemoDO;
import com.mrathena.dao.manager.y2019.demo.DemoManager;
import com.mrathena.spring.boot.starter.api.BasePageResDTO;
import com.mrathena.spring.boot.starter.api.business.y2019.demo.CreateDemoReqDTO;
import com.mrathena.spring.boot.starter.api.business.y2019.demo.DemoDTO;
import com.mrathena.spring.boot.starter.api.business.y2019.demo.QueryDemoReqDTO;
import com.mrathena.spring.boot.starter.api.business.y2019.demo.QueryDemoResDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;

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

	public boolean createDemo(CreateDemoReqDTO request) {
		DemoDO demoDO = new DemoDO();
		demoDO.setDemo(request.getDemo());
		return demoManager.create(demoDO);
	}

	@Cacheable(cacheNames = "ONE_MINUTE", key = "'test'")
	public QueryDemoResDTO queryDemo(QueryDemoReqDTO request) {
		DemoDO demoDO = demoManager.queryDemoById(request.getId());
		if (ObjectUtils.isEmpty(demoDO)) {
			throw new ServiceException("Demo不存在", "没有查到数据");
		}
		QueryDemoResDTO result = new QueryDemoResDTO();
		result.setDemo(demoDO.getDemo());
		return result;
	}

	public BasePageResDTO<DemoDTO> queryDemoWithPage(QueryDemoReqDTO request) throws IllegalAccessException {
		PageInfo<DemoDO> page = demoManager.queryDemoListByMapWithPage(new HashMap<>(8), request.getPageSize(), request.getPageNo());
		return new BasePageResDTO<>(page.getTotal(), demoConverter.to(page.getList()));
	}

}
