package com.myProject.reggie.service.implementation;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myProject.reggie.entity.Employee;
import com.myProject.reggie.mapper.EmployeeMapper;
import com.myProject.reggie.service.EmployeeService;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper	, Employee> implements EmployeeService {

	public EmployeeServiceImpl() {
		// TODO Auto-generated constructor stub
	}

}
