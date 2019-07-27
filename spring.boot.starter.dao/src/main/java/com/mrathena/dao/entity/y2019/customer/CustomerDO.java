package com.mrathena.dao.entity.y2019.customer;

import com.mrathena.dao.entity.BaseDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * customer
 *
 * @author mrathena on 2019/07/27 23:13
 */
@Getter
@Setter
@Accessors(chain = true)
@ToString(callSuper = true)
public class CustomerDO extends BaseDO {

	/**
	 * 	username 用户名
	 */
	private String username;

	/**
	 * 	nickname 昵称
	 */
	private String nickname;
}
