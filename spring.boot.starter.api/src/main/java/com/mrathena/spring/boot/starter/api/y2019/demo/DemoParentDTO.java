package com.mrathena.spring.boot.starter.api.y2019.demo;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author mrathena on 2019/11/19 10:15
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DemoParentDTO implements Serializable {

	@NotBlank
	private String fatherName;

	@NotBlank
	private String matherName;

}
