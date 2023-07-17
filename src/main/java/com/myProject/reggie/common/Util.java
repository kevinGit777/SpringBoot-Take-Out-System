package com.myProject.reggie.common;

import org.springframework.util.DigestUtils;

public class Util {
	
	//to help meta object handler get employeeID
	static ThreadLocal<Long> curUserEmployeeId = new ThreadLocal<>();
	
	/**
	 * @param password of employee
	 * 
	 * @return
	 */
	public static String toMD5Password(String password) {
		return DigestUtils.md5DigestAsHex(password.getBytes());
	}
	
	public static void setCurUserEmployeeId(Long id) {
			curUserEmployeeId.set(id);
	}
	
	public static Long getCurUserEmployeeId() {
		return curUserEmployeeId.get();
	}
	
	
	
}
