package com.mrathena.dao.mapper.y2020.customer;

import com.mrathena.dao.entity.y2020.customer.CustomerDO;

import java.util.List;

/**
 * customer
 *
 * @author mrathena on 2020/04/26 09:42
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

	/**
	 * selectByCustomerNo
	 *
	 * @param customerNo .
	 * @return .
	 */
	CustomerDO selectByCustomerNo(String customerNo);

	/**
	 * selectAll
	 *
	 * @return .
	 */
	List<CustomerDO> selectAll();
}