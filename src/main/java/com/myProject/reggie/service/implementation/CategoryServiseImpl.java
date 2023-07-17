package com.myProject.reggie.service.implementation;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myProject.reggie.entity.Category;
import com.myProject.reggie.mapper.CategoryMapper;
import com.myProject.reggie.service.CategoryServise;

@Service
public class CategoryServiseImpl extends ServiceImpl< CategoryMapper, Category> implements CategoryServise {

}
