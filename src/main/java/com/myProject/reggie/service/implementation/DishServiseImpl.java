package com.myProject.reggie.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myProject.reggie.dto.DishDto;
import com.myProject.reggie.entity.Dish;
import com.myProject.reggie.entity.DishFlavor;
import com.myProject.reggie.mapper.DishMapper;
import com.myProject.reggie.service.DishFlavorServise;
import com.myProject.reggie.service.DishServise;

@Service
public class DishServiseImpl extends ServiceImpl<DishMapper, Dish> implements DishServise {

	@Autowired
	private DishFlavorServise dishFlavorServise;
	
	@Override
	@Transactional
	public void saveWithFlavor(DishDto dishDto) {
		this.save(dishDto);
		
		
		for (DishFlavor	 flavor : dishDto.getFlavors()) {
			flavor.setDishId( dishDto.getId());
		}
		
		dishFlavorServise.saveBatch(dishDto.getFlavors());
		
	}
	
	


}
