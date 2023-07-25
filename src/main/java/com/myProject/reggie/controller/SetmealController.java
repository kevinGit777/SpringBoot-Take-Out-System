package com.myProject.reggie.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myProject.reggie.common.R;
import com.myProject.reggie.customExpection.ItemActiveException;
import com.myProject.reggie.dto.SetmealDto;
import com.myProject.reggie.entity.Dish;
import com.myProject.reggie.entity.Setmeal;
import com.myProject.reggie.entity.SetmealDish;
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

	@Autowired
	CommonController commonController;

	@PostMapping("")
	public R<String> addSetMeal(@RequestBody SetmealDto setmealDto) {
		if (setmealServise.saveWithDish(setmealDto)) {
			return R.success("Success.");
		}

		return R.error("Something is wrong");

	}

	@PutMapping("")
	public R<String> updateSetmeal(@RequestBody SetmealDto setmealDto) {

		if (setmealServise.updateWithDish(setmealDto)) {
			return R.success("Success.");
		}

		return R.error("Something is wrong");

	}

	@GetMapping("/page")
	public R<Page<SetmealDto>> getSetMealPage(int page, int pageSize, String name) {

		Page<Setmeal> setmealPage = new Page<>(page, pageSize);
		Page<SetmealDto> setmealDtoPage = new Page<>(page, pageSize);

		LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.like(name != null, Setmeal::getName, name);
		queryWrapper.orderByDesc(Setmeal::getUpdateTime);

		setmealServise.page(setmealPage, queryWrapper);

		BeanUtils.copyProperties(setmealPage, setmealDtoPage, "records");

		List<SetmealDto> setmealDtoPageList = setmealPage.getRecords().stream().map((setmeal) -> {

			SetmealDto setmealDto = new SetmealDto();

			BeanUtils.copyProperties(setmeal, setmealDto);

			String categoryNameString = categoryServise.getById(setmeal.getCategoryId()).getName();

			setmealDto.setCategoryName(categoryNameString);

			return setmealDto;
		}).collect(Collectors.toList());

		setmealDtoPage.setRecords(setmealDtoPageList);

		return R.success(setmealDtoPage);

	}

	@GetMapping("/{id}")
	public R<SetmealDto> getSetMeal(@PathVariable Long id) {
		Setmeal setmeal = setmealServise.getById(id);

		SetmealDto setmealDto = new SetmealDto();

		BeanUtils.copyProperties(setmeal, setmealDto);

		LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();

		queryWrapper.eq(SetmealDish::getSetmealId, id);

		setmealDto.setSetmealDishes(setmealDishServise.list(queryWrapper));

		return R.success(setmealDto);

	}

	@PostMapping("/status/{status}")
	public R<String> changeStatus(@RequestParam List<Long> ids, @PathVariable int status) {

		for (Long id : ids) {
			Setmeal setmeal = setmealServise.getById(id);
			setmeal.setStatus(status);
			if (!setmealServise.updateById(setmeal)) {
				return R.error("Something is wrong at status changing.");
			}
		}

		return R.success("Status has updated to " + status);

	}

	@DeleteMapping("")
	public R<String> deleteSetmeal(@RequestParam List<Long> ids) {

		for (Long id : ids) {
			if (setmealServise.getById(id).getStatus() == 1) {
				throw new ItemActiveException("Setmeal "
						+ setmealServise.getById(id).getName()
						+ " is availiable for customer.");

			}
		}

		List<String> imgLocationStrings = ids.stream().map((id) -> {

			return setmealServise.getById(id).getImage();
		}).collect(Collectors.toList());
		
		setmealServise.removeByIds(ids);

		commonController.deleteImages(imgLocationStrings);

		return R.success("Item with " + ids + " has been Remvoed");
	}

}
