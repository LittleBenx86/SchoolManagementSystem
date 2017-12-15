package cn.scau.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具
 * @author Unicorn
 *	2017-10-03
 */
public final class MD5Util {
	
	private MD5Util() {
		
	}
	
	public static String encoderByMD5(String str) {
		StringBuilder s = new StringBuilder();
		MessageDigest md5 = null;
		
		try {
			md5 = MessageDigest.getInstance("MD5");
			md5.update(str.getBytes("utf-8"));
			for (byte b : md5.digest()) {
				s.append(String.format("%02X", b));//10进制转换为16进制,02表示不足两位前面补0输出
			}
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s.toString();
	}
}
