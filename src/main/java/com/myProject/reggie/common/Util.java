package com.myProject.reggie.common;

import org.springframework.util.DigestUtils;

public class Util {

	/**
	 * @param password of employee
	 * 
	 * @return
	 */
	public static String toMD5Password(String password) {
		return DigestUtils.md5DigestAsHex(password.getBytes());
	}
}
