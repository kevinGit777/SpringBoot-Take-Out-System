package com.myProject.reggie.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.myProject.reggie.common.R;
import com.myProject.reggie.common.Util;
import com.myProject.reggie.entity.ShoppingCart;
import com.myProject.reggie.service.ShoppingCartServise;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {


	@Autowired
	private ShoppingCartServise shoppingCartServise;
	
	
	@PostMapping("/add")
	public R<ShoppingCart> add( @RequestBody ShoppingCart cart) {
		
		Long userID = Util.getCurId("User");
		
		LambdaQueryWrapper<ShoppingCart> existQueryWrapper = new LambdaQueryWrapper<>();
		existQueryWrapper.eq(ShoppingCart::getUserId, userID );
		
		if( cart.getDishId() != null )
		{
			existQueryWrapper.eq(ShoppingCart::getDishId, cart.getDishId() );
		}else {
			existQueryWrapper.eq( ShoppingCart::getSetmealId, cart.getSetmealId());
		}
		
		ShoppingCart prevCart = shoppingCartServise.getOne(existQueryWrapper);
		
		if( prevCart != null)
		{
			prevCart.setNumber( prevCart.getNumber() +1 );
			shoppingCartServise.updateById(prevCart);
			
		}else {
			cart.setNumber(1);
			cart.setUserId(userID);
			
			prevCart = cart;
			shoppingCartServise.save(cart);
		}
		
		return R.success(prevCart);
	}
	
	@PostMapping("/sub")
	public R<String> removeOne(@RequestBody ShoppingCart cart) {
		
		Long userID = Util.getCurId("User");
		
		LambdaQueryWrapper<ShoppingCart> existQueryWrapper = new LambdaQueryWrapper<>();
		existQueryWrapper.eq(ShoppingCart::getUserId, userID );
		
		if( cart.getDishId() != null )
		{
			existQueryWrapper.eq(ShoppingCart::getDishId, cart.getDishId() );
		}else {
			existQueryWrapper.eq( ShoppingCart::getSetmealId, cart.getSetmealId());
		}
		
		ShoppingCart prevCart = shoppingCartServise.getOne(existQueryWrapper);
		
		if( prevCart.getNumber() > 1)
		{
			prevCart.setNumber( prevCart.getNumber() - 1 );
			shoppingCartServise.updateById(prevCart);
		}else {
			shoppingCartServise.removeById(prevCart);
		}
		
		return R.success("Success");

	}
	
	@GetMapping("/list")
	public R<List<ShoppingCart>> list( ) {
		Long userID = Util.getCurId("User");
		LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(ShoppingCart::getUserId, userID );
		
		List<ShoppingCart> list= shoppingCartServise.list(queryWrapper);
		
		if( list == null)
		{
			return R.error("ERROR AT SHOPPING CART LIST");
		}
		
		return  R.success(list);
	}
	
	@DeleteMapping("/clean")
	public R<String> removeAll()
	{
		Long userID = Util.getCurId("User");
		
		LambdaQueryWrapper<ShoppingCart> existQueryWrapper = new LambdaQueryWrapper<>();
		existQueryWrapper.eq(ShoppingCart::getUserId, userID );
		
		boolean status = shoppingCartServise.remove(existQueryWrapper);
		
		return status ? R.success("Clean All for user " + userID) : R.error("SQL return false");
			
	}
}
