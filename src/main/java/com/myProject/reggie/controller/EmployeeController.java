package com.myProject.reggie.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.myProject.reggie.common.R;
import com.myProject.reggie.entity.Employee;
import com.myProject.reggie.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	
	/**
	 * 
	 * @param request
	 * @param employee
	 * @return result with Employee
	 */
	@PostMapping("/login")
	public R<Employee> employeeLogin(HttpServletRequest request,  @RequestBody Employee employee) {
		
		String md5Password =  DigestUtils.md5DigestAsHex(employee.getPassword().getBytes());
		
		LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
		
		queryWrapper.eq(Employee::getUsername, employee.getUsername());
		
		Employee employeeFromQuery = employeeService.getOne(queryWrapper);
		
		
		if (employeeFromQuery == null || employeeFromQuery.getPassword().equals(md5Password)) {
			if(employeeFromQuery == null)
			{
				log.debug("login fail due to username not exist.");
			}else {
				log.debug("login fail due to password not match.");
			}
			return R.error("Login Fail."); // does not contain fail reason for security
		}
		
		if (employeeFromQuery.getStatus() != 1) {
			return R.error("Account has been supspended.");
		}
		
		request.getSession().setAttribute("employee", employeeFromQuery.getId());
		return R.success(employeeFromQuery);
	}

}		
