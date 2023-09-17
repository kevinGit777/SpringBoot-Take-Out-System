package com.myProject.reggie.service.implementation;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myProject.reggie.entity.Orders;
import com.myProject.reggie.mapper.OrderMapper;
import com.myProject.reggie.service.OrderServise;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderServise {


}
