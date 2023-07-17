package com.myProject.reggie.controller;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myProject.reggie.common.R;
import com.myProject.reggie.entity.Employee;
import com.myProject.reggie.service.EmployeeService;
import com.myProject.reggie.common.Util;

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
		
		String md5Password =  Util.toMD5Password(employee.getPassword());
		
		LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
		
		queryWrapper.eq(Employee::getUsername, employee.getUsername());
		
		Employee employeeFromQuery = employeeService.getOne(queryWrapper);
		
		
		if (employeeFromQuery == null || !employeeFromQuery.getPassword().equals(md5Password)) {
			if(employeeFromQuery == null)
			{
				log.info("login fail due to username not exist.");
			}else {
				log.info("login fail due to password not match.");
			}
			return R.error("Login Fail."); // does not contain fail reason for security
		}
		
		if (employeeFromQuery.getStatus() != 1) {
			return R.error("Account has been supspended.");
		}
		
		request.getSession().setAttribute("employee", employeeFromQuery.getId());
		
		//TODO: add a counter in sql to record the user login count
		log.info("login success.");
		return R.success(employeeFromQuery);
	}



	
	
	/**
	 * 
	 * @return success prompt on success, error on employee id not found. 
	 */
	@PostMapping("/logout")
	public R<String> employeeLogout(HttpServletRequest request) {
		if (request.getSession().getAttribute("employee") == null)
		{
			return R.error("Fail to get current user.");
		}
		
		request.getSession().removeAttribute("employee");
		return R.success("Logout Success!");
	}
	
	
	@PostMapping("")
	public R<String> addEmployee(HttpServletRequest request, @RequestBody Employee employee) {
		log.info("Add employee with info {}" , employee.toString());
		
		String defaultPasswordString = Util.toMD5Password("123456");
		employee.setPassword(defaultPasswordString);
		
		// implemented by meta object handeler
//		LocalDateTime nowDateTime = LocalDateTime.now();
//		employee.setCreateTime(nowDateTime);
//		employee.setUpdateTime(nowDateTime);
//		
//		Long cur_user = (long) request.getSession().getAttribute("employee"); 
//		employee.setCreateUser(cur_user);
//		employee.setUpdateUser(cur_user);
		
		
		//log.info("Adding user with full info: {}", employee.toString());
		employeeService.save(employee);
		
		return R.success("Create User success");
	}
	
	
	/**
	 * 
	 * @param page
	 * @param pageSize
	 * @param name 
	 * name for employee 
	 * @return
	 */
	@GetMapping("/page")
	public R<Page> employeeInfoPage(int page, int pageSize, String name) {
		log.info("Para: page {}, page size {}, name {}", page, pageSize, name);
		
		Page employeePage = new Page<>(page, pageSize);
		
		LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
		
		queryWrapper.like(StringUtils.checkValNotNull(name), Employee::getName,name);
		
		queryWrapper.orderByDesc(Employee::getUpdateTime);
		
		employeeService.page(employeePage, queryWrapper);
		
		return R.success(employeePage);
	}

	
	@PutMapping("")
	public R<String> updateEmployee(HttpServletRequest request, @RequestBody Employee employee) {
		
//		//TODO: add update user check to be admin
//		LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
//		
//		queryWrapper.eq(Employee::getUsername, "admin");
//		
//		Employee employeeFromQuery = employeeService.getOne(queryWrapper);
//		
//		if (employeeFromQuery.getId() != request.getSession().getAttribute("employee")) {
//			return R.error("Your are not admin, you cannot update user!");
//		}
		
		log.info("Get update request for Employee {}", employee.toString());
		
		employee.setUpdateTime(LocalDateTime.now());
		employee.setUpdateUser((Long) request.getSession().getAttribute("employee") );
		employeeService.updateById(employee);
		return R.success(String.format("Successfully update user %s", employee.getName()) );
	}
	
	
	@GetMapping("{id}")
	public R<Employee> getEmployeeById(@PathVariable Long id) {
		log.info("Get employee info with ID {}", id);
		
		Employee resEmployee = employeeService.getById(id);
		if(resEmployee == null) {
			return R.error("Cannot find ID with " + id.toString());
		}
		return R.success( resEmployee);
	}
}		
