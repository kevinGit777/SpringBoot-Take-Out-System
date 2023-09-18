package com.myProject.reggie.customExpection;

import com.myProject.reggie.common.Util;

public class OrderSubmitFailExpection extends RuntimeException {


	/**
	 *  auto generated UID
	 */
	private static final long serialVersionUID = -2453953499363240696L;

	public OrderSubmitFailExpection(String message) {
		super("User " + Util.getCurId("User") + message);
		// TODO Auto-generated constructor stub
	}



}
