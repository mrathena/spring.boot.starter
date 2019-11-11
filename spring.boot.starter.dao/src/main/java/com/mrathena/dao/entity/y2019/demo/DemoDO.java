package com.mrathena.dao.entity.y2019.demo;

import com.mrathena.dao.entity.BaseDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * demo
 *
 * @author mrathena on 2019/11/07 15:43
 */
@Getter
@Setter
@Accessors(chain = true)
@ToString(callSuper = true)
public class DemoDO extends BaseDO {

	/**
	 * demo demo
	 */
	private String demo;

}