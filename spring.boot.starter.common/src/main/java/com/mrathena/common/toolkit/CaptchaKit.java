package com.mrathena.common.toolkit;

/**
 * @author com.mrathena
 */
public final class CaptchaKit {

	public static void main(String[] args) {
		System.out.println(CaptchaKit.generateCaptcha(6));
		System.out.println(CaptchaKit.generateCaptcha(6));
		System.out.println(CaptchaKit.generateCaptcha(6));
		System.out.println(CaptchaKit.generateCaptcha(6));
	}

	private CaptchaKit() {}

	/**
	 * 生成短信验证码
	 */
	public static String generateCaptcha(int length) {
		return String.valueOf((long) ((Math.random() * 9 + 1) * Math.pow(10, length - 1)));
	}

}