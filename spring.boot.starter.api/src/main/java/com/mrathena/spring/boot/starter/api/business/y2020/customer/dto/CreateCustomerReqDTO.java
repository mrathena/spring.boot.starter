package com.mrathena.spring.boot.starter.api.business.y2020.customer.dto;

import com.mrathena.spring.boot.starter.api.business.BaseReqDTO;
import com.mrathena.spring.boot.starter.api.verify.annotation.Mobile;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerReqDTO extends BaseReqDTO {

	private static final long serialVersionUID = 1L;

	@NotBlank
	private String nickname;
	@NotBlank
	private String username;
	@NotBlank
	private String password;
	@NotBlank
	@Mobile
	private String mobile;
	@NotBlank
	@Email
	private String email;

}
