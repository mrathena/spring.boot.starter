package com.mrathena.common.toolkit;

import com.mrathena.common.exception.ServiceException;
import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author com.mrathena
 */
public final class FileKit {

	public static void main(String[] args) {
		String source = "D:/develop/_resource/jdk-8u144-windows-x64.exe";
		String target = "D:/develop/_resource/jdk.exe";
		long start = System.currentTimeMillis();
		FileKit.fileCopyByNio(source, target);
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}

	private FileKit() {}

	/**
	 * 循环删除目录/文件 (谨慎使用)
	 */
	public static void delete(File file) {
		if (!file.exists()) {
			return;
		}
		if (file.isFile()) {
			file.delete();
			return;
		}
		File[] files = file.listFiles();
		if (ArrayUtils.isEmpty(files)) {
			file.delete();
			return;
		}
		for (File file2 : files) {
			delete(file2);
		}
		file.delete();
	}

	/**
	 * 文件拷贝
	 */
	public static void fileCopy(String source, String target) {
		try (InputStream in = new FileInputStream(source);
		     OutputStream out = new FileOutputStream(target)) {
			byte[] buffer = new byte[4096];
			int bytesToRead;
			while ((bytesToRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesToRead);
			}
		} catch (Throwable cause) {
			throw new ServiceException(cause);
		}
	}

	/**
	 * 文件拷贝
	 */
	public static void fileCopyByNio(String source, String target) {
		try (FileInputStream in = new FileInputStream(source);
		     FileOutputStream out = new FileOutputStream(target)) {
			FileChannel inChannel = in.getChannel();
			FileChannel outChannel = out.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(4096);
			while (inChannel.read(buffer) != -1) {
				buffer.flip();
				outChannel.write(buffer);
				buffer.clear();
			}
		} catch (Throwable cause) {
			throw new ServiceException(cause);
		}
	}

}
