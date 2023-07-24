package com.myProject.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myProject.reggie.dto.SetmealDto;
import com.myProject.reggie.entity.Setmeal;

public interface SetmealServise extends IService<Setmeal> {

	boolean saveWithDish(SetmealDto setmealDto);
	
	boolean updateWithDish(SetmealDto setmealDto);
}
