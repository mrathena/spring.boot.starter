package com.mrathena.common.toolkit;

import com.mrathena.common.constant.Constant;
import com.mrathena.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.StringJoiner;

/**
 * @author com.mrathena on 2019/5/27 11:19
 */
@Slf4j
public final class IpKit {

	public static void main(String[] args) {
		System.out.println(IpKit.getLocalIp());
		System.out.println(IpKit.getLocalMac());
	}

	private IpKit() {}

	public static String getLocalIp() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (Throwable cause) {
			throw new ServiceException(cause);
		}
	}

	/**
	 * 获取本机Mac
	 */
	public static String getLocalMac() {
		try {
			StringJoiner sj = new StringJoiner(Constant.MINUS);
			InetAddress localHost = InetAddress.getLocalHost();
			byte[] macArray = NetworkInterface.getByInetAddress(localHost).getHardwareAddress();
			for (byte macPart : macArray) {
				String temp = Integer.toHexString(macPart & 0xFF);
				sj.add(temp.length() == 1 ? "0" + temp : temp);
			}
			return sj.toString();
		} catch (Throwable cause) {
			throw new ServiceException(cause);
		}
	}

}
