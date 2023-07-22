package com.myProject.reggie.service.implementation;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myProject.reggie.dto.DishDto;
import com.myProject.reggie.entity.Dish;
import com.myProject.reggie.entity.DishFlavor;
import com.myProject.reggie.mapper.DishMapper;
import com.myProject.reggie.service.DishFlavorServise;
import com.myProject.reggie.service.DishServise;

import java.util.List;

@Service
public class DishServiseImpl extends ServiceImpl<DishMapper, Dish> implements DishServise {

	@Autowired
	private DishFlavorServise dishFlavorServise;
	
	@Override
	@Transactional
	public boolean saveWithFlavor(DishDto dishDto) {
		this.save(dishDto);
		
		
		for (DishFlavor	 flavor : dishDto.getFlavors()) {
			flavor.setDishId( dishDto.getId());
		}
		
		return dishFlavorServise.saveBatch(dishDto.getFlavors());
		
	}

	@Override
	@Transactional
	public DishDto getWithFlavor(Long id) {
		Dish dish = this.getById(id);
		
		DishDto dishDto = new DishDto();
		
		BeanUtils.copyProperties(dish, dishDto);
		
		LambdaQueryWrapper< DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
		
		queryWrapper.eq(DishFlavor::getDishId, id);
	
		
		dishDto.setFlavors(dishFlavorServise.list(queryWrapper) );
		
		return dishDto;
	}

	@Override
	@Transactional
	public boolean updateWithFlavor(DishDto dishDto) {
		// TODO Auto-generated method stub
		
		this.updateById(dishDto);
		
		
		//update Flavor by first remove all flavor, then insert the report one
		LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		
		lambdaQueryWrapper.eq(DishFlavor::getDishId, dishDto.getId());
		
		dishFlavorServise.remove(lambdaQueryWrapper);
		
		for (DishFlavor flavor: dishDto.getFlavors())
		{
			flavor.setDishId(  dishDto.getId());
		}
		
		return dishFlavorServise.saveBatch(dishDto.getFlavors());
	}
	
	
	
	


}
