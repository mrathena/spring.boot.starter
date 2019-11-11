package com.mrathena.dao.mapper.y2019.demo;

import com.mrathena.dao.entity.y2019.demo.DemoDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * demo
 * 
 * @author mrathena on 2019/11/07 15:43
 */
public interface DemoMapper {

	/** 
	 * deleteByPrimaryKey
	 * 
	 * @param id .
	 * @return .
	 */
	int deleteByPrimaryKey(Long id);

	/** 
	 * insert
	 * 
	 * @param record .
	 * @return .
	 */
	int insert(DemoDO record);

	/** 
	 * insertSelective
	 * 
	 * @param record .
	 * @return .
	 */
	int insertSelective(DemoDO record);

	/** 
	 * selectByPrimaryKey
	 * 
	 * @param id .
	 * @return .
	 */
	DemoDO selectByPrimaryKey(Long id);

	/** 
	 * updateByPrimaryKeySelective
	 * 
	 * @param record .
	 * @return .
	 */
	int updateByPrimaryKeySelective(DemoDO record);

	/** 
	 * updateByPrimaryKey
	 * 
	 * @param record .
	 * @return .
	 */
	int updateByPrimaryKey(DemoDO record);

	/**
	 * selectByDemo
	 * @param demo .
	 * @return .
	 */
	List<DemoDO> selectByDemo(String demo);

	/**
	 * selectByDemoAndCreatedBy
	 * @param demo .
	 * @param createdBy .
	 * @return .
	 */
	List<DemoDO> selectByDemoAndCreatedBy(@Param("demo") String demo, @Param("createdBy") String createdBy);

	/**
	 * selectByMap
	 * @param map .
	 * @return .
	 */
	List<DemoDO> selectByMap(Map<String, Object> map);
}