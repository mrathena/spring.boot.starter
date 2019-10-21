package com.mrathena.spring.boot.starter.api.y2019.demo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author mrathena on 2019/10/17 21:46
 */
@Getter
@Setter
@ToString(callSuper = true)
public class DemoResDto implements Serializable {

	private String demoStringFiled;
	private List<DemoDto> demoObjectListFiled;

}
