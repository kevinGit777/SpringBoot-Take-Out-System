package com.myProject.reggie.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myProject.reggie.common.R;
import com.myProject.reggie.entity.Category;
import com.myProject.reggie.entity.Employee;
import com.myProject.reggie.service.CategoryServise;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryServise categoryServise;

	@PostMapping("")
	public R<String> addCategory(HttpServletRequest request, @RequestBody Category category) {
		categoryServise.save(category);
		return R.success("insert success");
	}
	
	
	/**
	 * 
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@GetMapping("/page")
	public R<Page> categotyInfoPage(int page, int pageSize) {
		log.info("Para: page {}, page size {}", page, pageSize);
		
		Page categoryPage = new Page<>(page, pageSize);
		
		LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
				
		queryWrapper.orderByAsc(Category::getSort);
		
		categoryServise.page(categoryPage, queryWrapper);
		
		return R.success(categoryPage);
	}
}
