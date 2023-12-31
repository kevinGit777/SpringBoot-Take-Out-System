package com.myProject.reggie.controller;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.myProject.reggie.common.R;
import com.myProject.reggie.common.ValidateCodeUtils;
import com.myProject.reggie.entity.User;
import com.myProject.reggie.service.EmailServise;
import com.myProject.reggie.service.UserServise;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

	@Autowired
	private EmailServise emailServise;

	@Autowired
	private UserServise userServise;
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@GetMapping("/validatecode")
	public R<String> ValidationCode(String email, HttpSession session) {
		String validateCode = ValidateCodeUtils.generateValidateCode4String(4);
		redisTemplate.opsForValue().set(email, validateCode, 5, TimeUnit.MINUTES );
		
		emailServise.SendValicationCode(validateCode, email);

		return R.success("Sent Code.");
	}

	@PostMapping("/login")
	public R<User> login(@RequestBody Map<String, String> map, HttpSession session, HttpServletRequest request) {
		String emailString = map.get("email");
		
		String expectedCode = redisTemplate.opsForValue().get(emailString);
		
		
		if (expectedCode == null) {
			return R.error("Please request validation code first.");
		}

		
		if( !StringUtils.equals( expectedCode , map.get("code")) )
		{
			return R.error("Login Fail.");
		}
		
		
		redisTemplate.delete(emailString);
		log.info("User login success.");
		//session.setAttribute("user", emailString);

		LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(User::getEmail, emailString);

		User curUser = userServise.getOne(queryWrapper);

		if (curUser == null) {
			curUser = new User();
			curUser.setEmail(emailString);
			curUser.setStatus(1);
			curUser.setPhone("13312345678");
			
			userServise.save(curUser);
			curUser = userServise.getOne(queryWrapper);
		}
		
		session.setAttribute("user", curUser.getId());
		
		
		return R.success(curUser);

	}
}
