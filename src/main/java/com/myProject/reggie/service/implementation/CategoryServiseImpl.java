package com.myProject.reggie.service.implementation;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myProject.reggie.CustomExpection.NullCategoryReferenceException;
import com.myProject.reggie.entity.Category;
import com.myProject.reggie.entity.Dish;
import com.myProject.reggie.entity.Setmeal;
import com.myProject.reggie.mapper.CategoryMapper;
import com.myProject.reggie.service.CategoryServise;
import com.myProject.reggie.service.DishServise;
import com.myProject.reggie.service.SetmealServise;

@Service
public class CategoryServiseImpl extends ServiceImpl< CategoryMapper, Category> implements CategoryServise {

	@Autowired
	private DishServise dishServise;
	
	@Autowired
	private SetmealServise setmealServise;
	

	
	@Override
	public boolean removeById(Serializable id) {

		LambdaQueryWrapper<Dish> dishLookUp = new LambdaQueryWrapper<>();
		dishLookUp.eq(Dish::getCategoryId, id);
		if( dishServise.count(dishLookUp) > 0)
		{
			throw new NullCategoryReferenceException("This category Contains one or more Dish.");
			
		}
		
		LambdaQueryWrapper<Setmeal> setmealLookUp = new LambdaQueryWrapper<Setmeal>();
		
		setmealLookUp.eq(Setmeal::getCategoryId, id);
		if(  setmealServise.count(setmealLookUp) > 0)
		{
			throw new NullCategoryReferenceException("This category Contains one or more SetMeal.");
			
		}
		
		return super.removeById(id);
	}
	
	
}
