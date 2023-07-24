package com.myProject.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myProject.reggie.dto.DishDto;
import com.myProject.reggie.entity.Dish;

public interface DishServise extends IService<Dish> {

	
	boolean saveWithFlavor(DishDto dishDto);
	
	DishDto getWithFlavor(Long id);
	
	boolean updateWithFlavor(DishDto dishDto);
	
}
