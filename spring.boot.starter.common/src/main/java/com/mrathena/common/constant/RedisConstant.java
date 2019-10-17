package com.mrathena.common.constant;

/**
 * @author mrathena on 2019/5/25 19:42
 */
public class RedisConstant {

	private RedisConstant() {}

	/**
	 * 用毫秒来表示 1s,1m,1h,1d,1month,1year
	 */
	public static final long SECOND_IN_MILLISECONDS = 1000L;
	public static final long MINUTE_IN_MILLISECONDS = SECOND_IN_MILLISECONDS * 60;
	public static final long HOUR_IN_MILLISECONDS = MINUTE_IN_MILLISECONDS * 60;
	public static final long DAY_IN_MILLISECONDS = HOUR_IN_MILLISECONDS * 24;
	public static final long MONTH_IN_MILLISECONDS = DAY_IN_MILLISECONDS * 30;
	public static final long YEAR_IN_MILLISECONDS = MONTH_IN_MILLISECONDS * 12;

	/**
	 * 用秒来表示 1m,1h,1d,1month,1year
	 */
	public static final long MINUTE_IN_SECONDS = 60L;
	public static final long HOUR_IN_SECONDS = MINUTE_IN_SECONDS * 60;
	public static final long DAY_IN_SECONDS = HOUR_IN_SECONDS * 24;
	public static final long MONTH_IN_SECONDS = DAY_IN_SECONDS * 30;
	public static final long YEAR_IN_SECONDS = MONTH_IN_SECONDS * 12;

	/**
	 * redis分布式锁默认超时时间(用毫秒来表示的一分钟)
	 */
	public static final long DISTRIBUTED_LOCK_TIMEOUT = MINUTE_IN_MILLISECONDS;

}
