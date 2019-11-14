package com.mrathena.spring.boot.starter.api.y2019.demo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author mrathena on 2019/11/7 17:20
 */
@Getter
@Setter
@ToString
public class DemoDTO implements Serializable {

	private Long id;
	private Date createdAt;
	private String createdBy;
	private Date updatedAt;
	private String updatedBy;
	private String demo;

	private String anotherDemo;

}
