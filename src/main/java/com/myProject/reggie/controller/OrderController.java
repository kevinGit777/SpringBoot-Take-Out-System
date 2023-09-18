package com.myProject.reggie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myProject.reggie.common.R;
import com.myProject.reggie.common.Util;
import com.myProject.reggie.entity.Employee;
import com.myProject.reggie.entity.Orders;
import com.myProject.reggie.service.OrderServise;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderServise orderServise;
	

	@PostMapping("/submit")
	public R<String> submit(@RequestBody Orders order ) {
		
		return orderServise.submit(order) ? R.success("Sumbit Order Success.") : R.error("ERROR AT ORDER SUBMIT.");
		
	}
	
	@GetMapping("/userPage")
	public R<Page<Orders>> getPage(Integer page, Integer pageSize) {
		
		Page<Orders> userPage = new Page<>(page, pageSize);
		
		LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
		
		queryWrapper.eq(Orders::getUserId,  Util.getCurId("User"));
		
		queryWrapper.orderByDesc(Orders::getOrderTime);
		
		orderServise.page(userPage, queryWrapper);
		
		return R.success(userPage);
	}

}
