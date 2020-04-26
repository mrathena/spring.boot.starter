package com.mrathena.dao.entity.y2020.customer;

import com.mrathena.dao.entity.BaseDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * customer
 * 
 * @author mrathena on 2020/04/26 09:42
 */
@Getter
@Setter
@Accessors(chain = true)
@ToString(callSuper = true)
public class CustomerDO extends BaseDO {

	/** 
	customer_no customerNo
	 */
	private String customerNo;

	/** 
	nickname nickname
	 */
	private String nickname;

	/** 
	username username
	 */
	private String username;

	/** 
	password password
	 */
	private String password;

	/** 
	mobile mobile
	 */
	private String mobile;

	/** 
	email email
	 */
	private String email;

}