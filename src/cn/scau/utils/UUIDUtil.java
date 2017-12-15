package cn.scau.utils;

import java.util.UUID;

/**
 * UUID序列码生成
 * @author Unicorn
 *	2017-10-03
 */
public final class UUIDUtil {
	
	private UUIDUtil() {
		
	}
	
	/**
	 * 去掉UUID附带的-而生成的序列码
	 * @return
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	//带-的32位uuid
	public static String getFullUUID() {
		return UUID.randomUUID().toString();
	}
	
	//4uuid
	public static String get4UUID() {
		return UUID.randomUUID().toString().split("-")[1];
	}
	
	//8uuid
	public static String get8UUID() {
		return UUID.randomUUID().toString().split("-")[0];
	}
	
	//12uuid
	public static String get12UUID() {
		return get4UUID() + get8UUID();
	}
	
	//16uuid
	public static String get16UUID() {
		return get8UUID() + get8UUID();
	}
	
	//20uuid
	public static String get20UUID() {
		return get16UUID() + get4UUID();
	}
	
	//24uuid
	public static String get24UUID() {
		return get16UUID() + get8UUID();
	}
	
}
