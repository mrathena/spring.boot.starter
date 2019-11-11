package com.mrathena.spring.boot.starter.api.y2019.demo;

import com.mrathena.spring.boot.starter.api.BaseReqDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author mrathena on 2019/10/17 21:42
 */
@Getter
@Setter
@ToString(callSuper = true)
public class CreateDemoReqDto extends BaseReqDto {

	private String demo;

}
