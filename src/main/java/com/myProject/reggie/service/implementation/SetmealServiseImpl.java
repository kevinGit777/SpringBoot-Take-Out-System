package com.myProject.reggie.service.implementation;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myProject.reggie.entity.Setmeal;
import com.myProject.reggie.mapper.SetmealMapper;
import com.myProject.reggie.service.SetmealServise;

@Service
public class SetmealServiseImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealServise {

}
