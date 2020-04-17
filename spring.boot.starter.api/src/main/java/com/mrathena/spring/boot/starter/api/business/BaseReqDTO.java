package com.mrathena.spring.boot.starter.api.business;

import com.mrathena.spring.boot.starter.api.verify.Verifiable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author mrathena on 2019/10/18 9:04
 */
@Getter
@Setter
@ToString(callSuper = true)
public abstract class BaseReqDTO implements Serializable, Verifiable {

	/**
	 * 透传追踪号
	 */
	@NotBlank
	private String traceNo;

	/**
	 * 通用分页参数
	 */
	private Integer pageSize;
	private Integer pageNo;
	private Integer offset;
	private Integer limit;

	public String getTraceNo() {
		return StringUtils.isBlank(this.traceNo) ? UUID.randomUUID().toString().replace("-", "") : this.traceNo;
	}

}
