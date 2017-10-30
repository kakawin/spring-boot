package com.kakawin.gis.springboot.utils;

import org.apache.shiro.crypto.hash.SimpleHash;

public class PasswordEncoder {

	private static String encode(String password, int hashIterations) {
		String hashed = password;
		for (int i = 0; i < hashIterations; i++) {
			hashed = new SimpleHash("MD5", hashed, null, 1).toHex();
		}
		return hashed;
	}

	public static String encode(String password) {
		return encode(password, 2);
	}

}
