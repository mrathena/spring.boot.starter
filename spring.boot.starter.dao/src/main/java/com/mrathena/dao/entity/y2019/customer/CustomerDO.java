package com.mrathena.dao.entity.y2019.customer;

import com.mrathena.dao.entity.BaseDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * t_customer
 *
 * @author mrathena on 2019/07/27 17:52
 */
@Getter
@Setter
@Accessors(chain = true)
@ToString(callSuper = true)
public class CustomerDO extends BaseDO {

	/**
	 * 	username
	 */
	private String username;
}
