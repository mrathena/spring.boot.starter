package com.mrathena.spring.boot.starter.api;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author mrathena on 2019/10/17 21:42
 */
@Getter
@Setter
@ToString(callSuper = true)
public class DemoReqDto implements Serializable {

	private String demoFiled;
	private Long demoFiled2;
	private Date demoFiled3;

}
