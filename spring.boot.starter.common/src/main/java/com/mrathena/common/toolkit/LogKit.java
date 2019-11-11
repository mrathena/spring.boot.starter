package com.mrathena.common.toolkit;

import org.slf4j.MDC;

/**
 * @author mrathena on 2019/11/7 18:23
 */
public final class LogKit {

	private LogKit() {}

	private static final String TRACE = "TRACE";

	public static void setTraceNo(String traceNo) {
		MDC.put(TRACE, traceNo);
	}

}
