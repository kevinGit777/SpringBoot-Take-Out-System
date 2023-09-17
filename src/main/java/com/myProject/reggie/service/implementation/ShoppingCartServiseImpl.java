package com.myProject.reggie.service.implementation;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myProject.reggie.entity.ShoppingCart;
import com.myProject.reggie.mapper.ShoppingCartMapper;
import com.myProject.reggie.service.ShoppingCartServise;

@Service
public class ShoppingCartServiseImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartServise {



}
