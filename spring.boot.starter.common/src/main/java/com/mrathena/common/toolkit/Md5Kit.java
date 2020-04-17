package com.mrathena.common.toolkit;

import com.mrathena.common.exception.ServiceException;

import java.security.MessageDigest;

/**
 * @author com.mrathena on 2019/5/27 11:34
 * <p>
 * 注意: MessageDigest 不是线程安全的, 不可以直接用成员变量, 每次使用需要重新获取实例 MessageDigest.getInstance(MD5);
 */
public final class Md5Kit {

	private Md5Kit() {}

	private static final String MD5 = "MD5";
	private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

	public static String getMd5(String string) {
		return getMd5(string.getBytes());
	}

	public static String getMd5(byte[] bytes) {
		try {
			MessageDigest md = MessageDigest.getInstance(MD5);
			md.update(bytes);
			return bufferToHex(md.digest());
		} catch (Throwable cause) {
			throw new ServiceException(cause);
		}
	}

	private static String bufferToHex(byte[] bytes) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte[] bytes, int m, int n) {
		StringBuilder stringBuilder = new StringBuilder(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			char c0 = HEX_DIGITS[(bytes[l] & 0xf0) >> 4];
			char c1 = HEX_DIGITS[bytes[l] & 0xf];
			stringBuilder.append(c0);
			stringBuilder.append(c1);
		}
		return stringBuilder.toString();
	}

}
