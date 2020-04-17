package com.mrathena.spring.boot.starter.api.business.y2019.demo;

import com.mrathena.spring.boot.starter.api.business.BaseReqDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author mrathena on 2019/10/17 21:42
 */
@Getter
@Setter
@ToString(callSuper = true)
public class QueryDemoReqDTO extends BaseReqDTO {

	private Long id;

}
