package com.mrathena.spring.boot.starter.api.business.y2019.demo;

import com.mrathena.api.verify.annotation.Enumeration;
import com.mrathena.api.verify.annotation.Mobile;
import com.mrathena.api.verify.annotation.Token;
import com.mrathena.spring.boot.starter.api.BaseReqDTO;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author mrathena on 2019/10/17 21:42
 */
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CreateDemoReqDTO extends BaseReqDTO {

	@NotBlank
	private String demo;

	@Mobile
	private String productNo;

	@Token
	private String token;

	private String channel;

	@Enumeration(enumClass = DemoChannelEnum.class, allowNull = true)
	private String channel2;

	/**
	 * 如果某字段是自定义对象类型, 且该字段的子字段也需要做校验, 则需要使用 javax.validation.Valid 注解
	 */
	@NotNull
	@Valid
	private DemoParentDTO parent;

}
