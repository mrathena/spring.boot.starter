package com.mrathena.dao.manager.y2019.demo;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mrathena.common.constant.Constant;
import com.mrathena.dao.entity.y2019.demo.DemoDO;
import com.mrathena.dao.mapper.y2019.demo.DemoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author mrathena on 2019/11/7 15:46
 */
@Slf4j
@Component
public class DemoManager {

	@Resource
	private DemoMapper demoMapper;

	public boolean create(DemoDO demoDO) {
		Date now = new Date();
		demoDO.setCreatedAt(now);
		demoDO.setUpdatedAt(now);
		demoDO.setCreatedBy(Constant.SYSTEM);
		demoDO.setUpdatedBy(Constant.SYSTEM);
		return demoMapper.insertSelective(demoDO) > 0;
	}

	public DemoDO queryDemoById(long id) {
		return demoMapper.selectByPrimaryKey(id);
	}

	public List<DemoDO> queryDemoListByDemo(String demo) {
		return demoMapper.selectByDemo(demo);
	}

	public List<DemoDO> queryDemoListByDemoAndCreatedBy(String demo, String createdBy) {
		return demoMapper.selectByDemoAndCreatedBy(demo, createdBy);
	}

	public List<DemoDO> queryDemoListByCondition(String demo, String createdBy) {
		return demoMapper.selectByDemoAndCreatedBy(demo, createdBy);
	}

	public List<DemoDO> queryDemoListByMap(Map<String, Object> map) {
		return demoMapper.selectByMap(map);
	}

	public PageInfo<DemoDO> queryDemoListByMapWithPage(Map<String, Object> map, int pageSize, int pageNo) {
		PageHelper.startPage(pageNo, pageSize);
		List<DemoDO> demoDoList = demoMapper.selectByMap(map);
		return new PageInfo<>(demoDoList);
	}

	public PageInfo<DemoDO> queryDemoListByConditionWithPage(String demo, String createdBy, int pageSize, int pageNo) {
		PageHelper.startPage(pageNo, pageSize);
		List<DemoDO> demoDoList = demoMapper.selectByDemoAndCreatedBy(demo, createdBy);
		return new PageInfo<>(demoDoList);
	}

	public PageInfo<DemoDO> queryDemoListByByDemoAndCreatedByWithPage(String demo, String createdBy, int pageSize, int pageNo) {
		PageHelper.startPage(pageNo, pageSize);
		List<DemoDO> demoDoList = demoMapper.selectByDemoAndCreatedBy(demo, createdBy);
		return new PageInfo<>(demoDoList);
	}

}
