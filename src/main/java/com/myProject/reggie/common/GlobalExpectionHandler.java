package com.myProject.reggie.common;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.myProject.reggie.customExpection.NullCategoryReferenceException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExpectionHandler {

	
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public R<String> SQLConstraintExpectionHandler(SQLIntegrityConstraintViolationException exception) {
		log.error("Exception of {} with error code {}", exception.getMessage() , exception.getSQLState());
		
		if(exception.getMessage().contains("Duplicate entry") )
		{
			
			String usernameString = exception.getMessage().split(" ")[2];
			return R.error(String.format("%s has been used. Please enter another one.", usernameString) );
			
		}
		
		return R.error("Unknown SQL Expection.");
		
	}
	
	@ExceptionHandler(NullCategoryReferenceException.class)
	public R<String> nullCategoryReferenceExpectionHandle(NullCategoryReferenceException exception) {
		log.error("Catch NullCategoryReferenceException with msg {}", exception.getMessage());
		
		return R.error(exception.getMessage());
	}
	
}
