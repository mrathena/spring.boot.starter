package com.mrathena.remote.demo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author mrathena on 2020-03-29 05:55
 */
@Getter
@Setter
@ToString
public class CustomerQueryReqDto implements Serializable {

	private String mobile;

}
