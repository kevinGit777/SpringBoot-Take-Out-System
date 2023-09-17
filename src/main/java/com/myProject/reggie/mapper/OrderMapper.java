package com.myProject.reggie.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myProject.reggie.entity.Orders;

@Mapper
public interface OrderMapper extends BaseMapper<Orders> {

}
