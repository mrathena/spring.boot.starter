package com.mrathena.spring.boot.starter.api.business.y2020.customer.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class CustomerDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String customerNo;
	private String nickname;
	private String username;
	private String password;
	private String mobile;
	private String email;

}
