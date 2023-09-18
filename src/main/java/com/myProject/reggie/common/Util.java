package com.myProject.reggie.common;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.DigestUtils;

import com.myProject.reggie.entity.AddressBook;

public class Util {
	
	//to help meta object handler get employeeID
	static ThreadLocal<Long> curEmployeeId = new ThreadLocal<>();
	static ThreadLocal<Long> curUserId = new ThreadLocal<>();
	
	/**
	 * @param password of employee
	 * 
	 * @return
	 */
	public static String toMD5Password(String password) {
		return DigestUtils.md5DigestAsHex(password.getBytes());
	}
	
	public static void setCurId(Long id, String target) {
		if( StringUtils.equals(target, "employee"))
		{
			setCurEmployeeId(id);
		}else {
			setCurUserId(id);
		}
	}
	
	public static Long getCurId(String target) {
		if( StringUtils.equals(target, "employee"))
		{
			return getCurEmployeeId();
		}else {
			return getCurUserId();
		}
	}
	
	static void setCurEmployeeId(Long id) {
		curEmployeeId.set(id);
	}
	
	 static Long getCurEmployeeId() {
		return curEmployeeId.get();
	}
	
	 static void setCurUserId(Long id) {
		curUserId.set(id);
	}
	
	 static Long getCurUserId() {
		return curUserId.get();
	}
	 
	 public static String getFulAddress(AddressBook addressBook) {
		
		 StringBuilder sb = new StringBuilder();
		 
		 
		 String provinceName = addressBook.getProvinceName() == null ? "" :addressBook.getProvinceName();
		 sb.append(provinceName);
		 
		 String cityName = addressBook.getCityName() == null ? "" : addressBook.getCityName();
		 sb.append(cityName);
		 
		 String districtString = addressBook.getDistrictName() == null? "" : addressBook.getDistrictName();
		 sb.append(districtString);
		 
		 String detailString = addressBook.getDetail() == null? "" : addressBook.getDetail();
		 
		 sb.append(detailString);
		 
		 return sb.toString();
	}
	
	
	
}
