package com.mrathena.dao.manager.y2020.customer;

import com.mrathena.common.constant.Constant;
import com.mrathena.dao.entity.y2020.customer.CustomerDO;
import com.mrathena.dao.mapper.y2020.customer.CustomerMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Component
public class CustomerManager {

	@Resource
	private CustomerMapper mapper;

	public boolean createCustomer(CustomerDO customer) {
		customer.setCreatedAt(new Date()).setCreatedBy(Constant.SYSTEM).setUpdatedAt(customer.getCreatedAt()).setUpdatedBy(Constant.SYSTEM);
		return mapper.insertSelective(customer) > 0;
	}

	public CustomerDO queryCustomerByCustomerNo(String customerNo) {
		return mapper.selectByCustomerNo(customerNo);
	}

	public List<CustomerDO> queryAllCustomerList() {
		return mapper.selectAll();
	}

}
