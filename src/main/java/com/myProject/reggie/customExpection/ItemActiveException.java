package com.myProject.reggie.customExpection;

public class ItemActiveException extends RuntimeException {

	/**
	 * Auto generate serialVersionID
	 */
	private static final long serialVersionUID = -3089304912124335074L;

	public ItemActiveException(String message) {
		
		super(message);
	}


}
