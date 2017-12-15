package cn.scau.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA加密
 * @author Unicorn
 *	2017-10-03
 */
public final class SHAUtil {
	
	private SHAUtil() {
		// TODO Auto-generated constructor stub
	}
	
	public static String encoderBySHA(String str) {
		StringBuilder s = new StringBuilder();
		StringBuilder salt = new StringBuilder(str);
		MessageDigest md = null;
		
		try {
			md = MessageDigest.getInstance("SHA");
			md.update(salt.append(str).toString().getBytes("utf-8"));
			for (byte b : md.digest()) {
				s.append(String.format("%02X", b));
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return s.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(MD5Util.encoderByMD5(SHAUtil.encoderBySHA("admin@123")));
	}
	
}
