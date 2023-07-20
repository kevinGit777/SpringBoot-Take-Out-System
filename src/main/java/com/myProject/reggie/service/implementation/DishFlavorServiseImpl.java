package com.myProject.reggie.service.implementation;

import java.util.Collection;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myProject.reggie.entity.DishFlavor;
import com.myProject.reggie.mapper.DishFlavorMapper;
import com.myProject.reggie.service.DishFlavorServise;
import com.myProject.reggie.service.DishServise;

@Service
public class DishFlavorServiseImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorServise {

}
