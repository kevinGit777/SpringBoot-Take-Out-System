package com.myProject.reggie.service.implementation;

import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myProject.reggie.controller.CommonController;
import com.myProject.reggie.dto.SetmealDto;
import com.myProject.reggie.entity.Setmeal;
import com.myProject.reggie.entity.SetmealDish;
import com.myProject.reggie.mapper.SetmealMapper;
import com.myProject.reggie.service.SetmealDishServise;
import com.myProject.reggie.service.SetmealServise;

@Service
public class SetmealServiseImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealServise {

	@Autowired
	SetmealDishServise setmealDishServise;

	@Override
	@Transactional
	public boolean saveWithDish(SetmealDto setmealDto) {

		if (!this.save(setmealDto))
			return false;

		for (SetmealDish dish : setmealDto.getSetmealDishes()) {
			dish.setSetmealId(setmealDto.getId());

		}

		setmealDishServise.saveBatch(setmealDto.getSetmealDishes());
		return true;
	}

	@Override
	@Transactional
	public boolean updateWithDish(SetmealDto setmealDto) {
		// remove all setmeal dish then add back

		this.updateById(setmealDto);

		LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();

		queryWrapper.eq(SetmealDish::getSetmealId, setmealDto.getId());
		setmealDishServise.remove(queryWrapper);

		for (SetmealDish dish : setmealDto.getSetmealDishes()) {
			dish.setSetmealId(setmealDto.getId());
		}

		setmealDishServise.saveBatch(setmealDto.getSetmealDishes());

		return true;
	}

	@Override
	@Transactional
	public boolean removeByIds(Collection<? extends Serializable> idList) {

		if (!super.removeByIds(idList)) {
			return false;
		}

		LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<SetmealDish>();

		queryWrapper.in(SetmealDish::getSetmealId, idList);

		if (!setmealDishServise.remove(queryWrapper)) {
			return false;
		}

		return true;

	}

}
