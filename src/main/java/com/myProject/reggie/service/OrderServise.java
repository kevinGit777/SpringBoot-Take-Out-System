package com.myProject.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myProject.reggie.entity.Orders;


public interface OrderServise extends IService<Orders> {

	boolean submit(Orders order);

}
