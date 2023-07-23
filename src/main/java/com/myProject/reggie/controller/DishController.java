package com.myProject.reggie.controller;

import java.util.Arrays;
import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myProject.reggie.common.R;
import com.myProject.reggie.dto.DishDto;
import com.myProject.reggie.entity.Category;
import com.myProject.reggie.entity.Dish;
import com.myProject.reggie.service.CategoryServise;
import com.myProject.reggie.service.DishFlavorServise;
import com.myProject.reggie.service.DishServise;

import lombok.extern.slf4j.Slf4j;

/**
 * Also take care of the logic for Dishflavor
 * 
 * @author kevin
 *
 */
@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {

	@Autowired
	private DishServise dishServise;

	@Autowired
	private DishFlavorServise dishFlavorServise;

	@Autowired
	private CategoryServise categoryServise;

	@PostMapping("")
	public R<String> addDish(@RequestBody DishDto dishDto) {

		dishServise.saveWithFlavor(dishDto);

		return R.success("got object");
	}

	@GetMapping("/page")
	public R<Page<DishDto>> getDishPage(int page, int pageSize, String name) {

		Page<Dish> dishPage = new Page<>(page, pageSize);

		LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();

		queryWrapper.like(name != null, Dish::getName, name);

		queryWrapper.orderByDesc(Dish::getUpdateTime);

		dishServise.page(dishPage, queryWrapper);

		Page<DishDto> dishDtoPage = new Page<>(page, pageSize);

		BeanUtils.copyProperties(dishPage, dishDtoPage, "records");

		List<DishDto> dishDtoRecordsList = dishPage.getRecords().stream().map((dish) -> {
			DishDto dishDto = new DishDto();

			BeanUtils.copyProperties(dish, dishDto);

			Category category = categoryServise.getById(dish.getCategoryId());

			if (category != null) {
				dishDto.setCategoryName(category.getName());
			}

			return dishDto;
		}).collect(Collectors.toList());

		dishDtoPage.setRecords(dishDtoRecordsList);

		return R.success(dishDtoPage);

	}

	@GetMapping("/{id}")
	public R<DishDto> getDishWithFlavor(@PathVariable Long id) {

		DishDto resDishDto = dishServise.getWithFlavor(id);

		if (resDishDto != null)
			return R.success(resDishDto);

		return R.error("Fail getDishWithFlavor.");

	}

	@PutMapping("")
	public R<String> updateDish(@RequestBody DishDto dishDto) {

		if (dishServise.updateWithFlavor(dishDto)) {
			return R.success("Update success.");
		}

		return R.error("Something is wrong with updateDish.");
	}

	@PostMapping("/status/{status}")
	public R<String> changeStatus(Long[] ids, @PathVariable int status) {

		for (Long id : ids) {
			Dish dish = dishServise.getById(id);
			dish.setStatus(status);
			if (!dishServise.updateById(dish)) {
				return R.error("Something is wrong at status changing.");
			}
		}

		return R.success("Status has updated to " + status);

	}

	@DeleteMapping("")
	public R<String> deleteDish(Long[] ids) {

		for (Long id : ids)
		{
			if (!dishServise.removeById(id)) {
				return R.error("Sonthing is wrong with removing.");
			}

		}
		return R.success("Item with " + Arrays.toString( ids) + " has been Remvoed");

		
		
	}
	
	@GetMapping("/list")
	public R<List<Dish>> getDishByCategoryId(Long categoryId) {
		
		LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
		
		queryWrapper.eq(Dish::getCategoryId, categoryId);
		
		List<Dish> resDishs = dishServise.list(queryWrapper);
		
		if(resDishs == null)
			return R.error("Error at /dish/list");
		
		return R.success(resDishs);
	
	}

}
