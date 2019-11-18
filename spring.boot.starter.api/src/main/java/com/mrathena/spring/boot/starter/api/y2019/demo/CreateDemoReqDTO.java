package com.mrathena.spring.boot.starter.api.y2019.demo;

import com.mrathena.spring.boot.starter.api.BaseReqDTO;
import com.mrathena.spring.boot.starter.toolkit.verify.annotation.MemberOf;
import com.mrathena.spring.boot.starter.toolkit.verify.annotation.ProductNo;
import lombok.*;

import javax.validation.constraints.NotBlank;

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

	@MemberOf(members = {"APP", "H5"}, message = "渠道类型不正确")
	private String channel;

}
