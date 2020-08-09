package com.github.lybgeek.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;


@Slf4j
public class BeanUtils {
	private BeanUtils() {}
	/**
	 * 对象序列化为byte数组
	 * 
	 * @param obj
	 * @return
	 */
	public static byte[] serialize(Object obj) {
		byte[] bytes = null;
		try (ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
			 ObjectOutputStream outputStream = new ObjectOutputStream(byteArray)){
			outputStream.writeObject(obj);
			outputStream.flush();
			bytes = byteArray.toByteArray();
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
		return bytes;
	}
	/**
	 * 字节数组转为Object对象
	 * 
	 * @param bytes
	 * @return
	 */
	public static Object deserialize(byte[] bytes) {
		Object readObject = null;
		try (ByteArrayInputStream in = new ByteArrayInputStream(bytes);
			 ObjectInputStream inputStream = new ObjectInputStream(in)){
			 readObject = inputStream.readObject();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		} 
		return readObject;
	}
}