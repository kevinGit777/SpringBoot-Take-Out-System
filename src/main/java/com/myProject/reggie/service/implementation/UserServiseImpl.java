package com.myProject.reggie.service.implementation;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myProject.reggie.entity.User;
import com.myProject.reggie.mapper.UserMapper;
import com.myProject.reggie.service.UserServise;

@Service
public class UserServiseImpl extends ServiceImpl<UserMapper, User> implements UserServise {


}
