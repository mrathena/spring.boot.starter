package com.mrathena.spring.boot.starter.api.y2019.demo;

import com.mrathena.spring.boot.starter.api.BaseReqDTO;
import com.mrathena.spring.boot.starter.toolkit.verify.annotation.Channel;
import com.mrathena.spring.boot.starter.toolkit.verify.annotation.InEnum;
import com.mrathena.spring.boot.starter.toolkit.verify.annotation.ProductNo;
import com.mrathena.spring.boot.starter.toolkit.verify.annotation.Token;
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

	@ProductNo
	private String productNo;

	@Token
	private String token;

	@Channel
	private String channel;

	@InEnum(enumClass = DemoChannelEnum.class, allowNull = true)
	private String channel2;

	/**
	 * 如果某字段是自定义对象类型, 且该字段的子字段也需要做校验, 则需要使用 javax.validation.Valid 注解
	 */
	@NotNull
	@Valid
	private DemoParentDTO parent;

}
