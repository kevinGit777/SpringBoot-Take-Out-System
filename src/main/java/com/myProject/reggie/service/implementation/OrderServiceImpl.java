package com.myProject.reggie.service.implementation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myProject.reggie.common.Util;
import com.myProject.reggie.customExpection.OrderSubmitFailExpection;
import com.myProject.reggie.entity.AddressBook;
import com.myProject.reggie.entity.OrderDetail;
import com.myProject.reggie.entity.Orders;
import com.myProject.reggie.entity.ShoppingCart;
import com.myProject.reggie.entity.User;
import com.myProject.reggie.mapper.OrderMapper;
import com.myProject.reggie.service.AddressBookService;
import com.myProject.reggie.service.OrderDetailService;
import com.myProject.reggie.service.OrderServise;
import com.myProject.reggie.service.ShoppingCartServise;
import com.myProject.reggie.service.UserServise;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderServise {

	@Autowired
	ShoppingCartServise shoppingCartServise ;
	
	@Autowired
	AddressBookService addressBookService;
	
	
	@Autowired
	UserServise userServise;
	
	@Autowired
	OrderDetailService orderDetailService;
	
	
	@Override
	@Transactional
	public boolean submit(Orders order) {
		LambdaQueryWrapper<ShoppingCart> getShoppingCartQuery = new LambdaQueryWrapper<>();
		
		User curUser = userServise.getById( Util.getCurId("User") );
		Long orderIdLong = IdWorker.getId();
		order.setId(orderIdLong);
		order.setNumber( String.valueOf(orderIdLong)  );
		order.setUserId(curUser.getId());
		
		getShoppingCartQuery.eq( ShoppingCart::getUserId, order.getUserId());
		
		List<ShoppingCart> carts = shoppingCartServise.list(getShoppingCartQuery);
		
		if( carts == null || carts.size() == 0 )
		{
			throw new OrderSubmitFailExpection(" has empty shopping cart.");
		}

		
		
		AddressBook userAddressBook = addressBookService.getById( order.getAddressBookId());
		
		if(userAddressBook == null)
		{
			throw new OrderSubmitFailExpection(" has invalid addressbook.");
		}
		
		
		order.setStatus( Orders.TO_BE_PAID);
		order.setOrderTime( LocalDateTime.now());
		order.setCheckoutTime( LocalDateTime.ofEpochSecond(0, 0, ZoneOffset.UTC  )); //represent a far past, never paid
		order.setUserName( curUser.getName() );
		order.setPhone(  userAddressBook.getPhone());
		order.setAddress( Util.getFulAddress(userAddressBook) );
		order.setConsignee( userAddressBook.getConsignee());
		
		BigDecimal amountBigDecimal= new BigDecimal(0);

		for( ShoppingCart cart: carts  )
		{
			amountBigDecimal.add( cart.getAmount().multiply(  new BigDecimal(cart.getNumber())) );
			OrderDetail	orderDetail = new OrderDetail();
			
			BeanUtils.copyProperties(cart, orderDetail);
			
			//orderDetail.setAmount( cart.getAmount().multiply(  new BigDecimal(cart.getNumber())) );
			orderDetail.setOrderId(orderIdLong);
			
			if ( !orderDetailService.save(orderDetail))
			{
				return false;
			}
			
		}
		
		shoppingCartServise.remove(getShoppingCartQuery);
		
		order.setAmount(amountBigDecimal);
		
		if( !this.save(order))
		{
			return false;
		}
		
		
		return true;
	}


}
