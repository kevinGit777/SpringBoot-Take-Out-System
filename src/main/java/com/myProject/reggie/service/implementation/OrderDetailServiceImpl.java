package com.myProject.reggie.service.implementation;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myProject.reggie.entity.OrderDetail;
import com.myProject.reggie.mapper.OrderDetailMapper;
import com.myProject.reggie.service.OrderDetailService;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {


}
