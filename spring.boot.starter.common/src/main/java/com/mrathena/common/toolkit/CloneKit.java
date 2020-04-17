package com.mrathena.common.toolkit;

import com.mrathena.common.exception.ServiceException;

import java.io.*;

/**
 * @author com.mrathena on 2019/5/27 11:16
 */
public final class CloneKit {

	public static void main(String[] args) {
		String mrathena = "com/mrathena";
		String clone = CloneKit.clone(mrathena);
		System.out.println(clone);
//		System.out.println(clone == com.mrathena);
	}

	private CloneKit() {}

	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T clone(T object) {
		T newObject;
		try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		     ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
			objectOutputStream.writeObject(object);
			try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
			     ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
				newObject = (T) objectInputStream.readObject();
			}
			return newObject;
		} catch (Throwable cause) {
			throw new ServiceException(cause);
		}
	}

}
