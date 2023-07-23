package com.myProject.reggie.controller;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myProject.reggie.common.R;
import com.myProject.reggie.dto.SetmealDto;
import com.myProject.reggie.entity.Setmeal;
import com.myProject.reggie.service.CategoryServise;
import com.myProject.reggie.service.SetmealDishServise;
import com.myProject.reggie.service.SetmealServise;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetmealController {

	@Autowired
	SetmealDishServise setmealDishServise;

	@Autowired
	SetmealServise setmealServise;
	
	@Autowired
	CategoryServise categoryServise;

	@PostMapping("")
	public R<String> addSetMeal(@RequestBody SetmealDto setmealDto) {
		if (setmealServise.saveWithDish(setmealDto)) {
			return R.success(null);
		}

		return R.error("Something is wrong");

	}
	
	@GetMapping("/page")
	public R<Page<SetmealDto>> getSetMealPage(int page, int pageSize, String name) {
		
		Page<Setmeal> setmealPage = new Page<>(page, pageSize);
		Page<SetmealDto> setmealDtoPage = new Page<>(page, pageSize);
		
		LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.like(name != null, Setmeal::getName, name);
		
		setmealServise.page(setmealPage, queryWrapper);
		
		BeanUtils.copyProperties(setmealPage, setmealDtoPage, "records");
		
		List<SetmealDto> setmealDtoPageList = setmealPage.getRecords().stream().map((setmeal)->{
			
			SetmealDto setmealDto = new SetmealDto();
			
			BeanUtils.copyProperties(setmeal, setmealDto);
			
			String categoryNameString = categoryServise.getById( setmeal.getCategoryId() ).getName();
			
			setmealDto.setCategoryName(categoryNameString);
			
			return setmealDto;
		}).collect(Collectors.toList());
		
		
		setmealDtoPage.setRecords(setmealDtoPageList);	
		
		return R.success(setmealDtoPage);
		
	}

}
