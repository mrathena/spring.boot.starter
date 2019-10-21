package com.mrathena.spring.boot.starter.api.y2019.demo;

import com.mrathena.spring.boot.starter.api.BaseReqDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author mrathena on 2019/10/17 21:42
 */
@Getter
@Setter
@ToString(callSuper = true)
public class DemoReqDto extends BaseReqDto {

	private String demoStringFiled;
	private Long demoLongFiled;
	private Date demoDateFiled;

}
