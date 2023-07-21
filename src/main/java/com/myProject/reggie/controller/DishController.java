package com.myProject.reggie.controller;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myProject.reggie.common.R;
import com.myProject.reggie.dto.DishDto;
import com.myProject.reggie.entity.DishFlavor;
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

	@PostMapping("")
	public R<String> addDish( @RequestBody DishDto dishDto) {
		
		dishServise.saveWithFlavor(dishDto);
	
		return R.success("got object");
	}
}
