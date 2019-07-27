package com.mrathena.dao.mapper.y2019.customer;

import com.mrathena.dao.entity.y2019.customer.CustomerDO;

/**
 * t_customer
 *
 * @author mrathena on 2019/07/27 17:52
 */
public interface CustomerMapper {

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
	int insert(CustomerDO record);

	/**
	 * insertSelective
	 *
	 * @param record .
	 * @return .
	 */
	int insertSelective(CustomerDO record);

	/**
	 * selectByPrimaryKey
	 *
	 * @param id .
	 * @return .
	 */
	CustomerDO selectByPrimaryKey(Long id);

	/**
	 * updateByPrimaryKeySelective
	 *
	 * @param record .
	 * @return .
	 */
	int updateByPrimaryKeySelective(CustomerDO record);

	/**
	 * updateByPrimaryKey
	 *
	 * @param record .
	 * @return .
	 */
	int updateByPrimaryKey(CustomerDO record);
}
